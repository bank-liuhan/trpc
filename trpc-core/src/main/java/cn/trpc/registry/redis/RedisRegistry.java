package cn.trpc.registry.redis;

import cn.trpc.common.tools.URIUtil;
import cn.trpc.registry.NotifyListener;
import cn.trpc.registry.RegistryService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.lang.annotation.Target;
import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Program: trpc
 * @ClassName: RedisRegistry
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 20:05
 * @Description: redis注册服务
 * @Version: V1.0
 */
public class RedisRegistry implements RegistryService {

    private URI address;
    private static final int TIME_OUT = 15; // 超时时间
    private List<URI> heartbeat = new ArrayList<>(); // 心跳检测
    private ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(5); // 延时队列

    private JedisPubSub jedisPubSub; // 订阅
    private Map<String, Set<URI>> localCache = new ConcurrentHashMap<>(); // 本地缓存
    private Map<String, NotifyListener> listenerMap = new ConcurrentHashMap<>(); // 通知集合


    @Override
    public void registry(URI uri) {
        try {
            /* 1. 组装key */
            String key = "trpc-" + uri.toString();

            /* 2. 连接redis */
            Jedis jedis = new Jedis(address.getHost(), address.getPort());

            /* 3. 注册 */
            jedis.setex(key, TIME_OUT, String.valueOf(System.currentTimeMillis()));

            /* 4. 关闭redis连接*/
            jedis.close();

            /* 5. 开启心跳检测 */
            heartbeat.add(uri);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribe(String serverName, NotifyListener notifyListener) {
        try {
            /* 第一次订阅 */
            if (localCache.get(serverName) == null) {
                /* 初始化 */
                localCache.putIfAbsent(serverName, new HashSet<>());
                listenerMap.putIfAbsent(serverName, notifyListener);

                /* 获取服务实例 */
                Jedis jedis = new Jedis(address.getHost(), address.getPort());
                Set<String> serviceInterfaces = jedis.keys("trpc-*" + serverName + "?*");
                for (String serviceInterface : serviceInterfaces) {
                    URI serviceUri = new URI(serviceInterface.replace("trpc-", ""));
                    localCache.get(serverName).add(serviceUri);
                }
                notifyListener.notify(localCache.get(serverName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(URI address) {
        this.address = address;

        /* 1. 开始延迟心跳检测 */
        executorService.scheduleWithFixedDelay(() -> {
            try {
                /* 连接redis */
                Jedis jedis = new Jedis(address.getHost(), address.getPort());
                for (URI uri : heartbeat) {
                    String key = "trpc-" + uri.toString();
                    jedis.expire(key, TIME_OUT);
                }
                /* 关闭redis连接 */
                jedis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 3000, 5000, TimeUnit.MILLISECONDS);

        /* 2. 开启监听 */
        executorService.execute(() -> {
            try {
                jedisPubSub = new JedisPubSub() {

                    @Override
                    public void onPSubscribe(String pattern, int subscribedChannels) {
                        System.out.println("注册中心开始监听：" + pattern);
                    }

                    @Override
                    public void onPMessage(String pattern, String channel, String message) {
                        /* 目前只处理 set expired 两个事件 */
                        try {
                            /* 获取到URI */
                            URI serviceUri = new URI(channel.replace("__keyspace@0__:trpc-", ""));

                            /* set */
                            if ("set".equals(message)) {
                                Set<URI> uris = localCache.get(URIUtil.getService(serviceUri));
                                if (uris != null) {
                                    uris.add(serviceUri);
                                }
                            }

                            /* expired */
                            if ("expired".equals(message)) {
                                Set<URI> uris = localCache.get(URIUtil.getService(serviceUri));
                                if (uris != null) {
                                    uris.remove(serviceUri);
                                }
                            }

                            /* 通知 */
                            if ("set".equals(message) || "expired".equals(message)) {
                                System.out.println("服务实例发生变化，开始刷新");
                                NotifyListener notifyListener = listenerMap.get(URIUtil.getService(serviceUri));
                                if (notifyListener != null) {
                                    notifyListener.notify(localCache.get(URIUtil.getService(serviceUri)));
                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                };

                /* 连接redis */
                Jedis jedis = new Jedis(address.getHost(), address.getPort());
                jedis.psubscribe(jedisPubSub, "__keyspace@0__:trpc-*");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

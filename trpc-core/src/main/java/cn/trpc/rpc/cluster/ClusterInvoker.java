package cn.trpc.rpc.cluster;

import cn.trpc.common.tools.SpiUtil;
import cn.trpc.config.ReferenceConfig;
import cn.trpc.config.RegistryConfig;
import cn.trpc.registry.NotifyListener;
import cn.trpc.registry.RegistryService;
import cn.trpc.rpc.Invoker;
import cn.trpc.rpc.RpcInvocation;
import cn.trpc.rpc.protocol.Protocol;

import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Program: trpc
 * @ClassName: ClusterInvoker
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 20:37
 * @Description: 集群
 * @Version: V1.0
 */
public class ClusterInvoker implements Invoker {

    private ReferenceConfig referenceConfig;

    private LoadBalance loadBalance;

    private Map<URI, Invoker> invokers = new ConcurrentHashMap<>();

    public ClusterInvoker(ReferenceConfig referenceConfig) {
        try {
            /* 注入对象配置 */
            this.referenceConfig = referenceConfig;
            /* 负载均衡 */
            this.loadBalance = (LoadBalance) SpiUtil.getServiceImpl(referenceConfig.getLoadBalance(), LoadBalance.class);
            /* 服务全类名 */
            String serviceName = referenceConfig.getServer().getName();

            /* 拉取所有服务实例 */
            for (RegistryConfig registryConfig : referenceConfig.getRegistryConfigs()) {
                URI address = new URI(registryConfig.getAddress());
                /* 获取注册服务 */
                RegistryService registryService = (RegistryService) SpiUtil.getServiceImpl(address.getScheme(), RegistryService.class);
                registryService.init(address);

                /* 订阅 */
                registryService.subscribe(serviceName, new NotifyListener() {
                    @Override
                    public void notify(Set<URI> uris) {
                        System.out.println("更新前的服务invoker信息" + invokers);
                        /* 删除 */
                        for (URI uri : invokers.keySet()) {
                            if (!uris.contains(uri)) {
                                invokers.remove(uri);
                            }
                        }

                        /* 新增 */
                        for (URI uri : uris) {
                            if (!invokers.containsKey(uri)) {
                                /* 新的服务 */
                                Protocol protocol = (Protocol) SpiUtil.getServiceImpl(uri.getScheme(), Protocol.class);
                                Invoker invoker = protocol.refer(uri);
                                invokers.put(uri,invoker);
                            }
                        }
                        System.out.println("更新后的服务invoker信息" + invokers);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Class getInterface() {
        return this.referenceConfig.getServer();
    }

    @Override
    public Object invoker(RpcInvocation rpcInvocation) throws Exception {
        Invoker invoker = this.loadBalance.select(invokers);
        Object result = invoker.invoker(rpcInvocation);
        return result;
    }
}

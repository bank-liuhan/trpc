package cn.trpc.config.spring.util;

import cn.trpc.common.tools.SpiUtil;
import cn.trpc.config.ProtocolConfig;
import cn.trpc.config.ReferenceConfig;
import cn.trpc.config.RegistryConfig;
import cn.trpc.config.ServerConfig;
import cn.trpc.registry.RegistryService;
import cn.trpc.rpc.Invoker;
import cn.trpc.rpc.cluster.ClusterInvoker;
import cn.trpc.rpc.protocol.Protocol;
import cn.trpc.rpc.proxy.ProxyFactory;

import java.net.NetworkInterface;
import java.net.URI;

/**
 * @Program: trpc
 * @ClassName: TrpcBootstrap
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 18:53
 * @Description: 封装
 * @Version: V1.0
 */
public class TrpcBootstrap {

    /* 暴露服务 */
    public static void export(ServerConfig serverConfig) {
        try {
            /* 1. 静态代理，获取对象 */
            Invoker invoker = ProxyFactory.getInvoker(serverConfig.getReference(), serverConfig.getServer());

            for (ProtocolConfig protocolConfig : serverConfig.getProtocolConfigs()) {
                /* 2. 拼凑exportURI */
                /* exportURI: protocolName://ip:port/serviceName?param=value&... */
                StringBuffer sb = new StringBuffer();
                sb.append(protocolConfig.getName() + "://");

                String hostAddress = NetworkInterface.getNetworkInterfaces().nextElement().getInterfaceAddresses().get(0).getAddress().getHostAddress();
                sb.append(hostAddress + ":");
                sb.append(protocolConfig.getPort() + "/");
                sb.append(serverConfig.getServer().getName() + "?");
                sb.append("transporter=" + protocolConfig.getTransporter() + "&");
                sb.append("serialization=" + protocolConfig.getSerialization());

                URI exportUri = new URI(sb.toString());
                System.out.println("准备暴露服务: " + exportUri);

                /* 3. 暴露服务 */
                Protocol protocol = (Protocol) SpiUtil.getServiceImpl(protocolConfig.getName(), Protocol.class);
                protocol.export(exportUri, invoker);

                /* 4. 注册服务 */
                for (RegistryConfig registryConfig : serverConfig.getRegistryConfigs()) {
                    /* 获取注册中心对象 */
                    URI address = new URI(registryConfig.getAddress());
                    RegistryService registryService = (RegistryService) SpiUtil.getServiceImpl(address.getScheme(), RegistryService.class);
                    registryService.init(address);
                    registryService.registry(exportUri);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    /* 注入服务 */
    public static Object reference(ReferenceConfig referenceConfig) {
        try {
            /* 1. 获取服务集群实例中的其中一个实例对象 */
            ClusterInvoker invoker = new ClusterInvoker(referenceConfig);

            /* 2. 动态代理获取代理对象 */
            Object proxy = ProxyFactory.getProxy(invoker, new Class[]{referenceConfig.getServer()});
            return proxy;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

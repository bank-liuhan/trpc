package cn.trpc.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Program: trpc
 * @ClassName: ReferenceConfig
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 18:46
 * @Description: 注入服务
 * @Version: V1.0
 */
public class ReferenceConfig {

    private List<ProtocolConfig> protocolConfigs;   // 协议

    private List<RegistryConfig> registryConfigs;   // 注册中心

    private Class server;   // 注入对象类类型

    private String loadBalance; // 负载均衡

    private String version; // 版本

    public synchronized void addProtocolConfig(ProtocolConfig protocolConfig) {
        if (protocolConfigs == null)
            protocolConfigs = new ArrayList<>();
        protocolConfigs.add(protocolConfig);
    }

    public synchronized void addRegistryConfig(RegistryConfig registryConfig) {
        if (registryConfigs == null)
            registryConfigs = new ArrayList<>();
        registryConfigs.add(registryConfig);
    }

    public List<ProtocolConfig> getProtocolConfigs() {
        return protocolConfigs;
    }

    public void setProtocolConfigs(List<ProtocolConfig> protocolConfigs) {
        this.protocolConfigs = protocolConfigs;
    }

    public List<RegistryConfig> getRegistryConfigs() {
        return registryConfigs;
    }

    public void setRegistryConfigs(List<RegistryConfig> registryConfigs) {
        this.registryConfigs = registryConfigs;
    }

    public Class getServer() {
        return server;
    }

    public void setServer(Class server) {
        this.server = server;
    }

    public String getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(String loadBalance) {
        this.loadBalance = loadBalance;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ReferenceConfig{" +
                "protocolConfigs=" + protocolConfigs +
                ", registryConfigs=" + registryConfigs +
                ", server=" + server +
                ", loadBalance='" + loadBalance + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}

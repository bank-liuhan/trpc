package cn.trpc.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Program: trpc
 * @ClassName: ServerConfig
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 18:42
 * @Description: 暴露服务
 * @Version: V1.0
 */
public class ServerConfig {

    private List<ProtocolConfig> protocolConfigs; // 协议集合

    private List<RegistryConfig> registryConfigs; // 注册集合

    private Class server;   // 暴露服务类对象

    private Object reference;   // 暴露服务实现类

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

    public Object getReference() {
        return reference;
    }

    public void setReference(Object reference) {
        this.reference = reference;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "protocolConfigs=" + protocolConfigs +
                ", registryConfigs=" + registryConfigs +
                ", server=" + server +
                ", reference=" + reference +
                ", version='" + version + '\'' +
                '}';
    }
}

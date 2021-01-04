package cn.trpc.config;

/**
 * @Program: trpc
 * @ClassName: ProtocolConfig
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 18:26
 * @Description: 协议
 * @Version: V1.0
 */
public class ProtocolConfig {

    /* 格式: trpc.protocol. */

    private String name;    // 协议名称
    private String port;    // 协议端口
    private String transporter;   // 传输协议
    private String serialization;   // 序列化方式

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getTransporter() {
        return transporter;
    }

    public void setTransporter(String transporter) {
        this.transporter = transporter;
    }

    public String getSerialization() {
        return serialization;
    }

    public void setSerialization(String serialization) {
        this.serialization = serialization;
    }


    @Override
    public String toString() {
        return "ProtocolConfig{" +
                "name='" + name + '\'' +
                ", port='" + port + '\'' +
                ", transporter='" + transporter + '\'' +
                ", serialization='" + serialization + '\'' +
                '}';
    }
}

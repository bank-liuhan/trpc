package cn.trpc.config;

/**
 * @Program: trpc
 * @ClassName: RegistryConfig
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 18:26
 * @Description: 注册中心
 * @Version: V1.0
 */
public class RegistryConfig {

    /* 格式：trpc.registry. */

    private String address; // 地址
    private String username;    // 用户名
    private String password;    // 密码

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegistryConfig{" +
                "address='" + address + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

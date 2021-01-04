package cn.trpc.registry;

import java.net.URI;

/**
 * @Program: trpc
 * @ClassName: RegistryService
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 20:02
 * @Description: 注册服务
 * @Version: V1.0
 */
public interface RegistryService {

    /* 注册 */
    void registry(URI uri);

    /* 订阅 */
    void subscribe(String serverName, NotifyListener notifyListener);

    /* 初始化 */
    void init(URI address);
}

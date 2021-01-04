package cn.trpc.registry;

import java.net.URI;
import java.util.Set;

/**
 * @Program: trpc
 * @ClassName: NotifyListener
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 20:01
 * @Description: 通知监听
 * @Version: V1.0
 */
public interface NotifyListener {

    /* 通知 */
    void notify(Set<URI> uris);
}

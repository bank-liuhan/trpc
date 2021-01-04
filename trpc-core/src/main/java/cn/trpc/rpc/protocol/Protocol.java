package cn.trpc.rpc.protocol;

import cn.trpc.rpc.Invoker;

import java.net.URI;

/**
 * @Program: trpc
 * @ClassName: Protocol
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 19:15
 * @Description: 协议
 * @Version: V1.0
 */
public interface Protocol {

    /* 暴露服务 */
    void export(URI exportUri, Invoker invoker);

    /* 注入服务 */
    Invoker refer(URI uri);
}

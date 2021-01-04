package cn.trpc.rpc.cluster;

import cn.trpc.rpc.Invoker;

import java.net.URI;
import java.util.Map;

/**
 * @Program: trpc
 * @ClassName: LoadBalance
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 20:35
 * @Description: 负载均衡
 * @Version: V1.0
 */
public interface LoadBalance {

    /* 选择实例对象 */
    Invoker select(Map<URI, Invoker> invokers) throws Exception;
}

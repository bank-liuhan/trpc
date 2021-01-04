package cn.trpc.rpc.cluster.loadbalance;

import cn.trpc.rpc.Invoker;
import cn.trpc.rpc.cluster.LoadBalance;

import java.net.URI;
import java.util.Map;
import java.util.Random;

/**
 * @Program: trpc
 * @ClassName: RandomLoadBalance
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 20:36
 * @Description: 随机负载均衡
 * @Version: V1.0
 */
public class RandomLoadBalance implements LoadBalance {

    @Override
    public Invoker select(Map<URI, Invoker> invokers) throws Exception {
        int index = new Random().nextInt(invokers.values().size());
        return invokers.values().toArray(new Invoker[]{})[index];
    }
}

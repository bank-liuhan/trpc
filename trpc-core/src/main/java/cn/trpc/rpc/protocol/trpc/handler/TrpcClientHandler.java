package cn.trpc.rpc.protocol.trpc.handler;

import cn.trpc.remoting.Handler;
import cn.trpc.remoting.TrpcChannel;
import cn.trpc.rpc.Response;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Program: trpc
 * @ClassName: TrpcClientHandler
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 20:48
 * @Description: trpc 客户端处理器
 * @Version: V1.0
 */
public class TrpcClientHandler implements Handler {

    /* 多线程场景下，消息应答 */
    private static final Map<Long, CompletableFuture> invokerMap = new ConcurrentHashMap<>();

    /* 登记 */
    public static CompletableFuture waitResult(long msgId) {
        CompletableFuture completableFuture = new CompletableFuture();
        invokerMap.put(msgId, completableFuture);
        return completableFuture;
    }


    @Override
    public void onReceive(TrpcChannel channel, Object msg) throws Exception {
        /* 客户端：接收相应对象 */
        Response response = (Response) msg;
        invokerMap.get(response.getRequestId()).complete(response); // 发送数据到对应的线程
        invokerMap.remove(response.getRequestId());
    }
}

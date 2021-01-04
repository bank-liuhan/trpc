package cn.trpc.rpc.protocol.trpc;

import cn.trpc.common.serialize.Serialization;
import cn.trpc.remoting.Client;
import cn.trpc.rpc.Invoker;
import cn.trpc.rpc.Response;
import cn.trpc.rpc.RpcInvocation;
import cn.trpc.rpc.protocol.trpc.handler.TrpcClientHandler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Program: trpc
 * @ClassName: TrpcClientInvoker
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 21:04
 * @Description: trpc客户端调用对象
 * @Version: V1.0
 */
public class TrpcClientInvoker implements Invoker {

    private Client client;
    private Serialization serialization;

    public TrpcClientInvoker(Client client, Serialization serialization) {
        this.client = client;
        this.serialization = serialization;
    }

    @Override
    public Class getInterface() {
        return null;
    }

    @Override
    public Object invoker(RpcInvocation rpcInvocation) throws Exception {

        /* 1. 编码 */
        byte[] bytes = serialization.serialize(rpcInvocation);

        /* 2. 发起请求 */
        this.client.getChannel().send(bytes);

        /* 3. 另一个线程等待相应 */
        CompletableFuture future = TrpcClientHandler.waitResult(rpcInvocation.getId());
        Object result = future.get(60, TimeUnit.SECONDS);

        /* 4. 处理响应 */
        Response response = (Response) result;

        if (response.getStatus() == 200) {
            return response.getContent();
        } else {
            throw new Exception("server error:" + response.getContent());
        }
    }
}

package cn.trpc.rpc.protocol.trpc.handler;

import cn.trpc.common.serialize.Serialization;
import cn.trpc.remoting.Handler;
import cn.trpc.remoting.TrpcChannel;
import cn.trpc.rpc.Invoker;
import cn.trpc.rpc.Response;
import cn.trpc.rpc.RpcInvocation;

/**
 * @Program: trpc
 * @ClassName: TrpcServerHandler
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 19:35
 * @Description: trpc 服务端 处理器
 * @Version: V1.0
 */
public class TrpcServerHandler implements Handler {

    private Serialization serialization; // 序列化

    private Invoker invoker; // 代理对象

    @Override
    public void onReceive(TrpcChannel channel, Object msg) throws Exception {
        /* 服务端：接收请求对象 */
        RpcInvocation rpcInvocation = (RpcInvocation) msg;
        System.out.println("收到rpcInvocation信息：" + rpcInvocation);

        Response response = new Response();
        response.setRequestId(rpcInvocation.getId());
        try {
            Object result = this.invoker.invoker(rpcInvocation);
            response.setStatus(200);
            response.setContent(result);
            System.out.println("服务端执行结果：" + result);
        } catch (Exception e) {
            response.setStatus(99);
            response.setContent(e.getMessage());
        }

        byte[] bytes = this.serialization.serialize(response);
        channel.send(bytes);
    }


    public Serialization getSerialization() {
        return serialization;
    }

    public void setSerialization(Serialization serialization) {
        this.serialization = serialization;
    }

    public Invoker getInvoker() {
        return invoker;
    }

    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }
}

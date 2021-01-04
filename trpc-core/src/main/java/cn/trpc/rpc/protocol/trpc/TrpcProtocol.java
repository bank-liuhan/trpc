package cn.trpc.rpc.protocol.trpc;

import cn.trpc.common.serialize.Serialization;
import cn.trpc.common.tools.SpiUtil;
import cn.trpc.common.tools.URIUtil;
import cn.trpc.remoting.Client;
import cn.trpc.remoting.Transporter;
import cn.trpc.rpc.Invoker;
import cn.trpc.rpc.Response;
import cn.trpc.rpc.RpcInvocation;
import cn.trpc.rpc.protocol.Protocol;
import cn.trpc.rpc.protocol.trpc.codec.TrpcCodec;
import cn.trpc.rpc.protocol.trpc.handler.TrpcClientHandler;
import cn.trpc.rpc.protocol.trpc.handler.TrpcServerHandler;

import java.net.URI;

/**
 * @Program: trpc
 * @ClassName: TrpcProtocol
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 19:16
 * @Description: trpc协议
 * @Version: V1.0
 */
public class TrpcProtocol implements Protocol {

    @Override
    public void export(URI exportUri, Invoker invoker) {

        /* 1. 获取序列化方式 */
        String serializationName = URIUtil.getParam(exportUri, "serialization");
        Serialization serialization = (Serialization) SpiUtil.getServiceImpl(serializationName, Serialization.class);

        /* 2. 实例化codec */
        TrpcCodec codec = new TrpcCodec();
        codec.setSerialization(serialization);
        codec.setClassType(RpcInvocation.class);

        /* 3. 实例化handler */
        TrpcServerHandler handler = new TrpcServerHandler();
        handler.setSerialization(serialization);
        handler.setInvoker(invoker);

        /* 4. 获取传输协议 */
        String transporterName = URIUtil.getParam(exportUri, "transporter");
        Transporter transporter = (Transporter) SpiUtil.getServiceImpl(transporterName, Transporter.class);

        /* 5. 启动服务 */
        transporter.start(exportUri, codec, handler);
    }

    @Override
    public Invoker refer(URI uri) {
        /* 1. 获取序列化方式 */
        String serializationName = URIUtil.getParam(uri, "serialization");
        Serialization serialization = (Serialization) SpiUtil.getServiceImpl(serializationName, Serialization.class);

        /* 2. 实例化codec */
        TrpcCodec codec = new TrpcCodec();
        codec.setSerialization(serialization);
        codec.setClassType(Response.class);

        /* 3. 实例化handler */
        TrpcClientHandler handler = new TrpcClientHandler();

        /* 4. 获取传输协议 */
        String transporterName = URIUtil.getParam(uri, "transporter");
        Transporter transporter = (Transporter) SpiUtil.getServiceImpl(transporterName, Transporter.class);

        /* 5. 建议长连接 */
        Client connect = transporter.connect(uri, codec, handler);
        TrpcClientInvoker invoker = new TrpcClientInvoker(connect, serialization);

        return invoker;
    }
}

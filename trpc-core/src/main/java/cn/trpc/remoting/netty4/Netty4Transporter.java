package cn.trpc.remoting.netty4;

import cn.trpc.remoting.*;

import java.net.URI;

/**
 * @Program: trpc
 * @ClassName: Netty4Transporter
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 19:43
 * @Description: netty4 传输协议
 * @Version: V1.0
 */
public class Netty4Transporter implements Transporter {

    @Override
    public Server start(URI uri, Codec codec, Handler handler) {
        Netty4Server server = new Netty4Server();
        server.start(uri, codec, handler);
        return server;
    }

    @Override
    public Client connect(URI uri, Codec codec, Handler handler) {
        Netty4Client client = new Netty4Client();
        client.connect(uri, codec, handler);
        return client;
    }
}

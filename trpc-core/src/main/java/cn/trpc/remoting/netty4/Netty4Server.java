package cn.trpc.remoting.netty4;

import cn.trpc.remoting.Codec;
import cn.trpc.remoting.Handler;
import cn.trpc.remoting.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.net.URI;

/**
 * @Program: trpc
 * @ClassName: Netty4Server
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 19:44
 * @Description: netty4服务端
 * @Version: V1.0
 */
public class Netty4Server implements Server {

    /* 事件轮询组 */
    private EventLoopGroup boss = new NioEventLoopGroup(); // 处理连接
    private EventLoopGroup worker = new NioEventLoopGroup(); // 处理逻辑

    @Override
    public void start(URI uri, Codec codec, Handler handler) {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(uri.getHost(), uri.getPort()))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new Netty4Codec(codec.getInstance()));
                            ch.pipeline().addLast(new Netty4Handler(handler));
                        }
                    });

            bootstrap.bind().sync();
            System.out.println("完成端口绑定和服务启动");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

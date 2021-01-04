package cn.trpc.remoting.netty4;

import cn.trpc.remoting.Client;
import cn.trpc.remoting.Codec;
import cn.trpc.remoting.Handler;
import cn.trpc.remoting.TrpcChannel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.net.URI;

/**
 * @Program: trpc
 * @ClassName: Netty4Client
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 20:56
 * @Description: netty4 客户端
 * @Version: V1.0
 */
public class Netty4Client implements Client {

    private TrpcChannel trpcChannel;

    /* 事件轮询组 */
    private EventLoopGroup group = new NioEventLoopGroup();

    @Override
    public void connect(URI uri, Codec codec, Handler handler) {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new Netty4Codec(codec.getInstance()));
                            ch.pipeline().addLast(new Netty4Handler(handler));
                        }
                    });
            ChannelFuture future = bootstrap.connect(uri.getHost(), uri.getPort()).sync();
            this.trpcChannel = new Netty4Channel(future.channel());

            /* 安全停机 */
            Runtime.getRuntime().addShutdownHook(new Thread(){
                @Override
                public void run() {
                    try {
                        System.out.println("我要停机了");
                        synchronized (Netty4Server.class) {
                            group.shutdownGracefully().sync();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public TrpcChannel getChannel() {
        return this.trpcChannel;
    }
}

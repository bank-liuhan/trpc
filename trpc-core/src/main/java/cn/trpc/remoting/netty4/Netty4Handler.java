package cn.trpc.remoting.netty4;

import cn.trpc.remoting.Handler;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Program: trpc
 * @ClassName: Netty4Handler
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 19:49
 * @Description: netty4 处理器
 * @Version: V1.0
 */
public class Netty4Handler extends ChannelDuplexHandler {

    private Handler handler;

    public Netty4Handler(Handler handler) {
        this.handler = handler;
    }

    /* 入栈 */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        handler.onReceive(new Netty4Channel(ctx.channel()), msg);
    }
}

package cn.trpc.remoting.netty4;

import cn.trpc.remoting.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.List;

/**
 * @Program: trpc
 * @ClassName: Netty4Codec
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 19:48
 * @Description: netty4 编解码
 * @Version: V1.0
 */
public class Netty4Codec extends ChannelDuplexHandler {

    private Codec codec;

    public Netty4Codec(Codec codec) {
        this.codec = codec;
    }

    /* 入栈 */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf = (ByteBuf) msg;

        byte[] bytes = new byte[buf.readableBytes()];

        buf.readBytes(bytes);

        List<Object> out = this.codec.decode(bytes);

        for (Object o : out) {
            ctx.fireChannelRead(o);
        }
    }

    /* 出栈 */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        byte[] encode = this.codec.encode(msg);
        super.write(ctx, Unpooled.wrappedBuffer(encode), promise);
    }
}

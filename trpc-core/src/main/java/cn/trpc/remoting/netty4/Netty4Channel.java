package cn.trpc.remoting.netty4;

import cn.trpc.remoting.TrpcChannel;
import io.netty.channel.Channel;

/**
 * @Program: trpc
 * @ClassName: Netty4Channel
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 19:59
 * @Description: Netty4 消息通道
 * @Version: V1.0
 */
public class Netty4Channel implements TrpcChannel {

    private Channel channel;

    public Netty4Channel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void send(byte[] msg) throws Exception {
        channel.writeAndFlush(msg);
    }
}

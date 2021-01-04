package cn.trpc.remoting;

/**
 * @Program: trpc
 * @ClassName: TrpcChannel
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 19:19
 * @Description: 消息通道
 * @Version: V1.0
 */
public interface TrpcChannel {

    void send(byte[] msg) throws Exception;
}

package cn.trpc.remoting;

/**
 * @Program: trpc
 * @ClassName: Handler
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 19:18
 * @Description: 处理器
 * @Version: V1.0
 */
public interface Handler {

    void onReceive(TrpcChannel channel, Object msg) throws Exception;
}

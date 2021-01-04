package cn.trpc.remoting;

import java.net.URI;

/**
 * @Program: trpc
 * @ClassName: Client
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 20:56
 * @Description: 客户端
 * @Version: V1.0
 */
public interface Client {

    void connect(URI uri, Codec codec, Handler handler);

    TrpcChannel getChannel();
}

package cn.trpc.remoting;

import java.net.URI;

/**
 * @Program: trpc
 * @ClassName: Server
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 19:45
 * @Description: 服务端
 * @Version: V1.0
 */
public interface Server {

    /* 启动服务 */
    void start(URI uri, Codec codec, Handler handler);

}

package cn.trpc.remoting;

import java.net.URI;

/**
 * @Program: trpc
 * @ClassName: Transporter
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 19:42
 * @Description: 传输协议
 * @Version: V1.0
 */
public interface Transporter {

    Server start(URI uri, Codec codec, Handler handler);


    Client connect(URI uri, Codec codec, Handler handler);
}

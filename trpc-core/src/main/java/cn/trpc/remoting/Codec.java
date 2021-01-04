package cn.trpc.remoting;

import java.util.List;

/**
 * @Program: trpc
 * @ClassName: Codec
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 19:17
 * @Description: 编解码器
 * @Version: V1.0
 */
public interface Codec {

    /* 编码 */
    byte[] encode(Object out) throws Exception;

    /* 解码 */
    List<Object> decode(byte[] data) throws Exception;

    /* 初始化 */
    Codec getInstance();
}

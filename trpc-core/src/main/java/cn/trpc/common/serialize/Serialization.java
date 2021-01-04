package cn.trpc.common.serialize;

/**
 * @Program: MyRPC
 * @ClassName: Serialization
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-24 20:57
 * @Description: 序列化
 * @Version: V1.0
 */
public interface Serialization {

    // 序列化
    byte[] serialize(Object output) throws Exception;


    // 反序列化
    Object deserialize(byte[] input, Class clazz) throws Exception;
}

package cn.trpc.rpc;

/**
 * @Program: trpc
 * @ClassName: Invoker
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 19:01
 * @Description: 统一调用对象接口
 * @Version: V1.0
 */
public interface Invoker {

    Class getInterface();

    Object invoker(RpcInvocation rpcInvocation) throws Exception;
}

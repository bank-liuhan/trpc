package cn.trpc.rpc.proxy;

import cn.trpc.rpc.Invoker;
import cn.trpc.rpc.RpcInvocation;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Program: trpc
 * @ClassName: ProxyFactory
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 19:04
 * @Description: 代理工厂
 * @Version: V1.0
 */
public class ProxyFactory {


    public static Invoker getInvoker(Object proxy, Class classType) {
        return new Invoker() {
            @Override
            public Class getInterface() {
                return classType;
            }

            @Override
            public Object invoker(RpcInvocation rpcInvocation) throws Exception {
                // 反射调用目标对象方法
                Method method = proxy.getClass().getMethod(rpcInvocation.getMethodName(), rpcInvocation.getParameterTypes());
                return method.invoke(proxy, rpcInvocation.getArguments());
            }
        };
    }

    public static Object getProxy(Invoker invoker, Class<?>[] interfaces) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, new InvokerInvocationHandler(invoker));
    }
}

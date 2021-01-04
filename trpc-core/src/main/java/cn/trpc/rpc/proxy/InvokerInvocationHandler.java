package cn.trpc.rpc.proxy;

import cn.trpc.rpc.Invoker;
import cn.trpc.rpc.RpcInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Program: trpc
 * @ClassName: InvokerInvocationHandler
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 21:13
 * @Description: 封装对象
 * @Version: V1.0
 */
public class InvokerInvocationHandler implements InvocationHandler {

    private Invoker invoker;

    public InvokerInvocationHandler(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        /* 无需远程调用的方法 */
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(proxy, args);
        }

        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();

        if (parameterTypes.length == 0) {
            if ("toString".equals(methodName)) {
                return invoker.toString();
            } else if ("$destroy".equals(methodName)) {
                return null;
            } else if ("hashCode".equals(methodName)) {
                return invoker.hashCode();
            }
        } else if (parameterTypes.length == 1 && "equals".equals(methodName)) {
            return invoker.equals(args[0]);
        }

        /* 发起远程调用 */
        RpcInvocation rpcInvocation = new RpcInvocation();
        rpcInvocation.setServiceName(method.getDeclaringClass().getName());
        rpcInvocation.setMethodName(methodName);
        rpcInvocation.setParameterTypes(parameterTypes);
        rpcInvocation.setArguments(args);

        Object result = this.invoker.invoker(rpcInvocation);
        return result;
    }
}

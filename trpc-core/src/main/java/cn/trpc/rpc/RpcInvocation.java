package cn.trpc.rpc;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Program: temp
 * @ClassName: RpcInvocation
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-19 21:16
 * @Description: PRC调用传递对象
 * @Version: V1.0
 */
public class RpcInvocation implements Serializable {

    /**
     *  保留一次调用相关的目的地，参数，每次都有唯一的ID
     */

    private static final long serialVersionUID = -4355285085441097045L;

    private static AtomicLong SEQ = new AtomicLong(); // 保证ID唯一

    private long id;
    private String serviceName; // 服务名称
    private String methodName; // 方法名称
    private Class<?>[] parameterTypes; // 参数类型
    private Object[] arguments; // 参数值

    public RpcInvocation() {
        this.setId(incrementAndGet());
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public AtomicLong getSEQ() {
        return SEQ;
    }

    public void setSEQ(AtomicLong SEQ) {
        this.SEQ = SEQ;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes == null ? new Class[0] : parameterTypes;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments == null ? new Object[0] : arguments;
    }

    @Override
    public String toString() {
        return "RpcInvocation{" +
                "SEQ=" + SEQ +
                ", id=" + id +
                ", serviceName='" + serviceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", arguments=" + Arrays.toString(arguments) +
                '}';
    }

    public final long incrementAndGet() {
        long current;
        long next;
        do {
            current = SEQ.get();
            next = current >= 2147483647 ? 0 : current + 1;
        } while (!SEQ.compareAndSet(current,next));
        return next;
    }
}

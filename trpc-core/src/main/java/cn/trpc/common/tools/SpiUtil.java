package cn.trpc.common.tools;

import java.util.ServiceLoader;

/**
 * @Program: temp
 * @ClassName: SpiUtil
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-19 20:14
 * @Description: SPI机制工具类
 * @Version: V1.0
 */
public class SpiUtil {

    /**
     *  获取指定服务类型的实现类
     * @param serviceName
     * @param classType
     * @return
     */
    public static Object getServiceImpl(String serviceName, Class classType) {
        // 通过SPI机制，获取到ClassType类型的所有实现类
        ServiceLoader services = ServiceLoader.load(classType, Thread.currentThread().getContextClassLoader());
        for (Object s : services) {
            // 匹配传入的服务名称
            if (s.getClass().getSimpleName().equals(serviceName)) {
                return s;
            }
        }
        return null;
    }
}

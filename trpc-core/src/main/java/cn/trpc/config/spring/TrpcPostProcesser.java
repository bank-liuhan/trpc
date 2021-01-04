package cn.trpc.config.spring;

import cn.trpc.config.ProtocolConfig;
import cn.trpc.config.ReferenceConfig;
import cn.trpc.config.RegistryConfig;
import cn.trpc.config.ServerConfig;
import cn.trpc.config.annotation.TrpcReference;
import cn.trpc.config.annotation.TrpcServer;
import cn.trpc.config.spring.util.TrpcBootstrap;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Field;

/**
 * @Program: trpc
 * @ClassName: TrpcPostProcesser
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 18:33
 * @Description: 实例化bean后续操作
 * @Version: V1.0
 */
public class TrpcPostProcesser implements ApplicationContextAware, InstantiationAwareBeanPostProcessor {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    /* 实例化Bean后执行相应的逻辑处理 */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        /* 1. 暴露服务， 注册服务 */
        if (bean.getClass().isAnnotationPresent(TrpcServer.class)) {

            ServerConfig serverConfig = new ServerConfig();
            serverConfig.addProtocolConfig(applicationContext.getBean(ProtocolConfig.class));
            serverConfig.addRegistryConfig(applicationContext.getBean(RegistryConfig.class));

            TrpcServer trpcServer = bean.getClass().getAnnotation(TrpcServer.class);
            if (trpcServer.interfaceClass() == void.class) {
                serverConfig.setServer(bean.getClass().getInterfaces()[0]);
            } else {
                serverConfig.setServer(trpcServer.interfaceClass());
            }
            serverConfig.setReference(bean);
            TrpcBootstrap.export(serverConfig);

        }

        /* 2. 注入服务 */
        for (Field field : bean.getClass().getDeclaredFields()) {
            try {
                if (!field.isAnnotationPresent(TrpcReference.class)) {
                    continue;
                }

                ReferenceConfig referenceConfig = new ReferenceConfig();
                referenceConfig.addProtocolConfig(applicationContext.getBean(ProtocolConfig.class));
                referenceConfig.addRegistryConfig(applicationContext.getBean(RegistryConfig.class));
                referenceConfig.setServer(field.getType());

                TrpcReference trpcReference = field.getAnnotation(TrpcReference.class);
                referenceConfig.setLoadBalance(trpcReference.loadBalance());

                Object reference = TrpcBootstrap.reference(referenceConfig);
                field.setAccessible(true);
                field.set(bean, reference);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bean;
    }
}

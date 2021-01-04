package cn.trpc.config.spring;

import cn.trpc.config.ProtocolConfig;
import cn.trpc.config.RegistryConfig;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Field;

/**
 * @Program: trpc
 * @ClassName: TrpcConfiguration
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 18:32
 * @Description: 初始化配置
 * @Version: V1.0
 */
public class TrpcConfiguration implements ImportBeanDefinitionRegistrar {

    /* 获取到所有的配置信息 */
    private StandardEnvironment environment;

    public TrpcConfiguration(Environment environment) {
        this.environment = (StandardEnvironment) environment;
    }


    /* 初始化配置信息 */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        BeanDefinitionBuilder builder = null;

        /* 初始化-协议 */
        builder = BeanDefinitionBuilder.genericBeanDefinition(ProtocolConfig.class);
        for (Field field : ProtocolConfig.class.getDeclaredFields()) {
            String value = environment.getProperty("trpc.protocol." + field.getName());
            builder.addPropertyValue(field.getName(), value);
        }
        registry.registerBeanDefinition("protocolConfig", builder.getBeanDefinition());

        /* 初始化-配置中心 */
        builder = BeanDefinitionBuilder.genericBeanDefinition(RegistryConfig.class);
        for (Field field : RegistryConfig.class.getDeclaredFields()) {
            String value = environment.getProperty("trpc.registry." + field.getName());
            builder.addPropertyValue(field.getName(), value);
        }
        registry.registerBeanDefinition("registryConfig", builder.getBeanDefinition());

        System.out.println("配置文件初始化完毕!");
    }
}

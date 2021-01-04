package cn.trpc.config.spring.annotation;

import cn.trpc.config.spring.TrpcConfiguration;
import cn.trpc.config.spring.TrpcPostProcesser;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Program: trpc
 * @ClassName: EnableTRPC
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 18:33
 * @Description: 开启trpc
 * @Version: V1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({TrpcConfiguration.class, TrpcPostProcesser.class})
public @interface EnableTRPC {
}

package cn.trpc;

import cn.trpc.config.spring.annotation.EnableTRPC;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

/**
 * @Program: trpc
 * @ClassName: SmsApplication
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 21:36
 * @Description: 启动类
 * @Version: V1.0
 */
@Configuration
@ComponentScan("cn.trpc")
@PropertySource("classpath:/trpc.properties")
@EnableTRPC
public class SmsApplication {

    public static void main(String[] args) throws IOException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SmsApplication.class);
        context.start();

        System.in.read();
        context.close();
    }
}

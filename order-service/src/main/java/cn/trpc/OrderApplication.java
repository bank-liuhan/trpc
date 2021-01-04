package cn.trpc;

import cn.trpc.api.order.OrderService;
import cn.trpc.config.spring.annotation.EnableTRPC;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Program: trpc
 * @ClassName: OrderApplication
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 21:29
 * @Description: 启动类
 * @Version: V1.0
 */
@Configuration
@ComponentScan("cn.trpc")
@PropertySource("classpath:/trpc.properties")
@EnableTRPC
public class OrderApplication {

    public static void main(String[] args) throws IOException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(OrderApplication.class);
        context.start();

        // 测试
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
        OrderService orderService = context.getBean(OrderService.class);
        for (int i = 0; i < 10; i++) {
            new Thread(){
                @Override
                public void run() {
                    try {
                        cyclicBarrier.await();
                        orderService.create("购买一块巧克力");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        System.in.read();
        context.close();
    }
}

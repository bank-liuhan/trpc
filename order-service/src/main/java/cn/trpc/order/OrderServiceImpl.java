package cn.trpc.order;

import cn.trpc.api.order.OrderService;
import cn.trpc.api.sms.SmsService;
import cn.trpc.config.annotation.TrpcReference;
import org.springframework.stereotype.Service;

/**
 * @Program: trpc
 * @ClassName: OrderServiceImpl
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 21:33
 * @Description: 订单
 * @Version: V1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    @TrpcReference
    private SmsService smsService;

    @Override
    public void create(String content) {
        System.out.println("订单: " + content + "创建完毕!");

        Object result = smsService.send("10086", content);

        System.out.println("rpc调用，响应结果: " + result);
    }
}

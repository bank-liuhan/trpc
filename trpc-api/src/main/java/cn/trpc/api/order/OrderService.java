package cn.trpc.api.order;

/**
 * @Program: trpc
 * @ClassName: OrderService
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 21:23
 * @Description: 订单
 * @Version: V1.0
 */
public interface OrderService {

    void create(String content); // 创建订单
}

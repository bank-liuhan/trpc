package cn.trpc.sms;

import cn.trpc.api.sms.SmsService;
import cn.trpc.config.annotation.TrpcServer;

import java.util.UUID;

/**
 * @Program: trpc
 * @ClassName: SmsServiceImpl
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 21:37
 * @Description: 短信
 * @Version: V1.0
 */
@TrpcServer
public class SmsServiceImpl implements SmsService {


    @Override
    public Object send(String phone, String content) {
        System.out.println("向: " + phone + " 发送短信, " + content);
        return "发送成功" + UUID.randomUUID().toString();
    }
}

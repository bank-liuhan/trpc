package cn.trpc.api.sms;

/**
 * @Program: trpc
 * @ClassName: SmsService
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-27 21:24
 * @Description: 短信
 * @Version: V1.0
 */
public interface SmsService {

    Object send(String phone, String content);
}

package cn.trpc.common.serialize.json;


import cn.trpc.common.serialize.Serialization;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

/**
 * @Program: temp
 * @ClassName: JsonSerialization
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-19 19:51
 * @Description: JSON序列化
 * @Version: V1.0
 */
public class JsonSerialization implements Serialization {

    // JSON序列化通过ObjectMapper对象来实现
    private static ObjectMapper objectMapper = new ObjectMapper();

    // 避免序列化过程中出现异常，提前对ObjectMapper进行设置
    static {
        // 对象的所有字段全部列入(非空)
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 取消默认转换timestamps对象
        objectMapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        // 忽略空BEAN转JSON的错误
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 所有日期统一格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 忽略在JSON字符串中存在，但是在BEAN中不存在 对应属性的情况，防止出错
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }


    @Override
    public byte[] serialize(Object output) throws Exception {
        byte[] bytes = objectMapper.writeValueAsBytes(output);
        return bytes;
    }

    @Override
    public Object deserialize(byte[] input, Class clazz) throws Exception {
        Object parse = objectMapper.readValue(input, clazz);
        return parse;
    }
}

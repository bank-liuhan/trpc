package cn.trpc.common.tools;

import java.net.URI;

/**
 * @Program: temp
 * @ClassName: URIUtil
 * @Author: cookie L
 * @Copyright 家里蹲股份有限公司
 * @Date: 2020-12-19 20:17
 * @Description: URI工具类
 * @Version: V1.0
 */
public class URIUtil {


    /**
     *  获取URI中指定的属性值
     * @param uri
     * @param paramName
     * @return
     */
    public static String getParam(URI uri, String paramName) {
        // 遍历URI中所有的参数
        for (String param : uri.getQuery().split("&")) {
            // 格式 paramName=paramValue
            if (param.startsWith(paramName + "=")) {
                return param.replace(paramName + "=" , "");
            }
        }
        return null;
    }

    /**
     *  获取服务
     * @param uri
     * @return
     */
    public static String getService(URI uri) {
        return uri.getPath().replace("/","");
    }
}

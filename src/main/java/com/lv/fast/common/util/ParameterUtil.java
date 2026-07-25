package com.lv.fast.common.util;

import com.google.common.collect.Maps;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;

/**
 * AOP 请求参数提取工具
 *
 * @author lvlaotou
 * @since 2026-07-25
 * @generated-by oh-my-pi (qwen3.8-max-preview)
 */
public class ParameterUtil {

    private ParameterUtil(){}

    /**
     * 获取请求参数（参数名 -> 参数值），无参数时返回空Map
     */
    public static LinkedHashMap<String, Object> getRequestParam(JoinPoint joinPoint){
        Assert.notNull(joinPoint, "JoinPoint is null");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();
        if (parameterNames != null && parameterNames.length > 0 && parameterValues != null && parameterValues.length == parameterNames.length){
            LinkedHashMap<String, Object> params = Maps.newLinkedHashMapWithExpectedSize(parameterNames.length);
            for (int i = 0; i < parameterNames.length; i++) {
                params.put(parameterNames[i], parameterValues[i]);
            }
            return params;
        }
        return Maps.newLinkedHashMap();
    }

    /**
     * 获取请求参数JSON（过滤MultipartFile类型的参数）
     */
    public static String getRequestParamJson(JoinPoint joinPoint){
        LinkedHashMap<String, Object> requestParam = getRequestParam(joinPoint);
        LinkedHashMap<String, Object> param = Maps.newLinkedHashMapWithExpectedSize(requestParam.size());
        requestParam.forEach((key, value) -> {
            if (!(value instanceof MultipartFile)) {
                param.put(key, value);
            }
        });
        return JsonUtil.toJson(param);
    }
}

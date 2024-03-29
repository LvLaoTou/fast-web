package com.lv.fast.common.util;

import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lvlaotou
 */
public class ParameterUtil {

    private ParameterUtil(){}

    /**
     * 获取请求参数
     */
    public static LinkedHashMap<String, Object> getRequestParam(JoinPoint joinPoint){
        Assert.notEmpty(joinPoint, "JoinPoint is null");
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
        return null;
    }

    @SneakyThrows
    public static String getRequestParamJson(JoinPoint joinPoint){
        Map<String, Object> requestParam = getRequestParam(joinPoint);
        Map<String, Object> param = null;
        if (requestParam != null) {
            param = requestParam.entrySet().stream()
                    .filter(entry -> !(entry.getValue() instanceof MultipartFile))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        return JsonUtil.toJson(param);
    }
}

package com.lv.fast.common.util;

import com.google.common.collect.Maps;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jie.lv
 */
public class ParameterUtil {

    private ParameterUtil(){}

    /**
     * 获取请求参数
     * @param joinPoint
     * @return
     */
    public static Map<String, Object> getRequestParam(JoinPoint joinPoint){
        Assert.assertNotNull(joinPoint, "JoinPoint is null");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();
        if (parameterNames != null && parameterNames.length > 0 && parameterValues != null && parameterValues.length == parameterNames.length){
            HashMap<String, Object> params = Maps.newHashMapWithExpectedSize(parameterNames.length);
            for (int i = 0; i < parameterNames.length; i++) {
                params.put(parameterNames[i], parameterValues[i]);
            }
            return params;
        }
        return null;
    }
}

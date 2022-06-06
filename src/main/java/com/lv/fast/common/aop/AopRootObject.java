package com.lv.fast.common.aop;

import cn.hutool.core.collection.CollectionUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * @author jie.lv
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AopRootObject {

    private Map<String, Object> param;

    private Method method;

    private Object result;

    private String errorMessage;

    public Object[] getArgs(){
        if (CollectionUtil.isEmpty(param)){
            return null;
        }
        Collection<Object> values = param.values();
        return CollectionUtil.isEmpty(values) ? null : values.toArray();
    }
}

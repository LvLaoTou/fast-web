package com.lv.fast.common.log;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;
import com.lv.fast.common.util.Assert;

import java.util.Map;
import java.util.Stack;

/**
 * 日志记录上下文
 * @author jie.lv
 */
public class LogRecordContext {

    private LogRecordContext(){}

    /**
     * 上下文变量存储容器
     */
    private final static InheritableThreadLocal<Stack<Map<String, Object>>> variableMapStack = new InheritableThreadLocal<>();

    public static void put(String key, Object value){
        Assert.assertNotNull(key, "key不能为空");
        Assert.assertNotNull(value, "value不能为空");
        Map<String, Object> variable = Maps.newHashMapWithExpectedSize(1);
        variable.put(key, value);
        put(variable);
    }

    public static void put(Map<String, Object> variable){
        Assert.assertNotNull(variable, "变量不能为空");
        Stack<Map<String, Object>> mapStack = variableMapStack.get();
        if (mapStack == null || mapStack.isEmpty()){
            mapStack = new Stack<>();
        }
        mapStack.push(variable);
        variableMapStack.set(mapStack);
    }

    public static Map<String, Object> listVariable(){
        Stack<Map<String, Object>> mapStack = variableMapStack.get();
        if (mapStack == null || mapStack.isEmpty()){
            return null;
        }
        return mapStack.peek();
    }

    public static Object get(String key){
        Map<String, Object> variableMap = listVariable();
        if (CollectionUtil.isEmpty(variableMap)){
            return null;
        }
        return variableMap.get(key);
    }

    public static void clear(){
        variableMapStack.remove();
    }
}

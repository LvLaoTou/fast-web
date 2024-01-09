package com.lv.fast.common.aop;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lv.fast.common.util.Assert;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 线程上下文
 * @author lvlaotou
 */
@Slf4j
public class AopContext {

    private AopContext(){}

    /**
     * 上下文变量存储容器
     */
    private final static InheritableThreadLocal<LinkedList<LinkedHashMap<String, Object>>> variableMapStack = new InheritableThreadLocal<>();

    public static void putVariable(String key, Object value){
        Assert.notEmpty(key, "key不能为空");
        Assert.notEmpty(value, "value不能为空");
        // 由线程日志切面完成初始化
        LinkedList<LinkedHashMap<String, Object>> mapStack = getStack();
        Assert.notEmpty(mapStack, "线程上下文未初始化");
        Map<String, Object> variableMap = mapStack.peek();
        variableMap.put(key, value);
    }

    public static LinkedHashMap<String, Object> listVariable(){
        LinkedList<LinkedHashMap<String, Object>> mapStack = getStack();
        if (mapStack == null || mapStack.isEmpty()){
            return null;
        }
        return mapStack.peek();
    }

    @SuppressWarnings("unused")
    public static Object getVariable(String key){
        Map<String, Object> variableMap = listVariable();
        if (CollectionUtil.isEmpty(variableMap)){
            return null;
        }
        return variableMap.get(key);
    }

    public static LinkedList<LinkedHashMap<String, Object>> getStack(){
        return variableMapStack.get();
    }

    public static void setStack(LinkedList<LinkedHashMap<String, Object>> stack){
        Assert.notEmpty(stack, "线程上下文环境变量栈不能为空");
        variableMapStack.set(stack);
    }

    public static void clear(){
        log.debug("清除环境变量线程上下文");
        variableMapStack.remove();
    }

    public static void initVariableThreadContext(){
        LinkedList<LinkedHashMap<String, Object>> stack = getStack();
        if (stack == null){
            stack = Lists.newLinkedList();
        }
        stack.push(Maps.newLinkedHashMap());
        AopContext.setStack(stack);
    }

    public static void clearVariableThreadContext(){
        LinkedList<LinkedHashMap<String, Object>> stack = getStack();
        if (stack == null){
            AopContext.clear();
        }else {
            stack.pop();
            if (stack.isEmpty()){
                AopContext.clear();
            }
        }
    }
}

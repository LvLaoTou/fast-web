package com.lv.fast.common.aop;

import cn.hutool.core.collection.CollectionUtil;
import com.lv.fast.common.util.Assert;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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
    private final static InheritableThreadLocal<Stack<Map<String, Object>>> variableMapStack = new InheritableThreadLocal<>();

    public static void putVariable(String key, Object value){
        Assert.notEmpty(key, "key不能为空");
        Assert.notEmpty(value, "value不能为空");
        // 由线程日志切面完成初始化
        Stack<Map<String, Object>> mapStack = getStack();
        Assert.notEmpty(mapStack, "线程上下文未初始化");
        Map<String, Object> variableMap = mapStack.peek();
        variableMap.put(key, value);
    }

    public static Map<String, Object> listVariable(){
        Stack<Map<String, Object>> mapStack = getStack();
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

    public static Stack<Map<String, Object>> getStack(){
        return variableMapStack.get();
    }

    public static void setStack(Stack<Map<String, Object>> stack){
        Assert.notEmpty(stack, "线程上下文环境变量栈不能为空");
        variableMapStack.set(stack);
    }

    public static void clear(){
        log.debug("清除环境变量线程上下文");
        variableMapStack.remove();
    }

    public static void initVariableThreadContext(){
        Stack<Map<String, Object>> stack = getStack();
        if (stack == null){
            stack = new Stack<>();
        }
        stack.push(new HashMap<>());
        AopContext.setStack(stack);
    }

    public static void clearVariableThreadContext(){
        Stack<Map<String, Object>> stack = getStack();
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

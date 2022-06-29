package com.lv.fast.common.log;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.lv.fast.common.aop.AopContext;
import com.lv.fast.common.aop.AopEvaluationContext;
import com.lv.fast.common.aop.AopRootObject;
import com.lv.fast.common.util.ParameterUtil;
import com.lv.fast.common.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author lvlaotou
 */
@Aspect
@Order
@Component
@Slf4j
@RequiredArgsConstructor
public class LogRecordAop {

    private final OperatorService operatorService;

    private final LogRecordService logRecordService;

    /**
     * Spring 参数解析器
     */
    private final LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    /**
     * spel 表达式解析器
     */
    private final ExpressionParser parser = new SpelExpressionParser();


    @Pointcut("@annotation(logRecord)")
    public void pointcut(LogRecord logRecord) {

    }

    @SneakyThrows
    @Around("pointcut(logRecord)")
    public Object around(ProceedingJoinPoint joinPoint, LogRecord logRecord){
        AopRootObject.AopRootObjectBuilder builder = AopRootObject.builder();
        Object result = null;
        try {
            // 初始化环境变量线程上下文
            initVariableThreadContext();
            result = joinPoint.proceed();
            return result;
        }catch (Throwable throwable){
            builder.errorMessage(throwable.getMessage());
            throw throwable;
        } finally {
            try {
                Map<String, Object> variable = AopContext.listVariable();
                // 清除线程上下问环境变量
                clearVariableThreadContext();
                Object finalResult = result;
                AtomicReference<Operator> operator = new AtomicReference<>(operatorService.getOperator());
                ThreadUtil.LOG_THREAD_POOL_EXECUTOR.execute(()->{
                    try{
                        Map<String, Object> params = ParameterUtil.getRequestParam(joinPoint);
                        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
                        Method method = methodSignature.getMethod();
                        builder.param(params).method(method);
                        if (finalResult != null){
                            builder.result(finalResult);
                        }
                        AopRootObject rootObject = builder.build();
                        AopEvaluationContext evaluationContext = new AopEvaluationContext(rootObject, discoverer, variable);
                        if (CollectionUtil.isNotEmpty(variable)){
                            variable.forEach(evaluationContext::setVariable);
                        }
                        // 是否记录日志
                        boolean isRecord = true;
                        String conditionSpel = logRecord.condition();
                        if (StrUtil.isNotBlank(conditionSpel)){
                            // 获取触发条件
                            isRecord = parser.parseExpression(conditionSpel).getValue(evaluationContext, Boolean.class);
                        }
                        if (isRecord){
                            boolean isSuccess = StrUtil.isBlank(rootObject.getErrorMessage());
                            String operatorSpel = logRecord.operator();
                            if (StrUtil.isNotBlank(operatorSpel)){
                                operator.set(parser.parseExpression(operatorSpel).getValue(evaluationContext, Operator.class));
                            }
                            String describe = null;
                            if (isSuccess){
                                String successSpel = logRecord.success();
                                if (StrUtil.isNotBlank(successSpel)){
                                    describe = parser.parseExpression(successSpel).getValue(evaluationContext, String.class);
                                }
                            }else {
                                String failSpel = logRecord.fail();
                                if (StrUtil.isNotBlank(failSpel)){
                                    describe = parser.parseExpression(failSpel).getValue(evaluationContext, String.class);
                                }else {
                                    describe = rootObject.getErrorMessage();
                                }
                            }

                            Record.RecordBuilder recordBuilder = Record.builder()
                                    .success(isSuccess)
                                    .operateType(logRecord.operateType())
                                    .operator(operator.get());
                            if (StrUtil.isNotBlank(describe)){
                                recordBuilder.describe(describe);
                            }
                            String bizNoSpel = logRecord.bizNo();
                            if (StrUtil.isNotBlank(bizNoSpel)){
                                String bizNo = parser.parseExpression(bizNoSpel).getValue(evaluationContext, String.class);
                                recordBuilder.bizNo(bizNo);
                            }
                            // 执行记录
                            logRecordService.record(recordBuilder.build());
                        }
                    }catch (Exception e){
                        log.error("记录操作日志异常:{}", e);
                    }
                });
            }catch (Exception e){
                log.error("记录操作日志异常", e);
            }
        }
    }

    private static void initVariableThreadContext(){
        Stack<Map<String, Object>> stack = AopContext.getStack();
        if (stack == null){
            stack = new Stack<>();
        }
        stack.push(new HashMap<>());
        AopContext.setStack(stack);
    }

    private static void clearVariableThreadContext(){
        Stack<Map<String, Object>> stack = AopContext.getStack();
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

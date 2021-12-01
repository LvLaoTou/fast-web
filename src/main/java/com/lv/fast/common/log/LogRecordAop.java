package com.lv.fast.common.log;

import com.lv.fast.common.util.ParameterUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Map;

/**
 * @author jie.lv
 */
@Aspect
@Order(1)
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
        LogRecordRootObject.LogRecordRootObjectBuilder builder = LogRecordRootObject.builder();
        try {
            Map<String, Object> params = ParameterUtil.getRequestParam(joinPoint);
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            Object result = joinPoint.proceed();
            builder.param(params)
                    .method(method)
                    .result(result);
            return result;
        }catch (Throwable throwable){
            builder.errorMessage(throwable.getMessage());
            throw throwable;
        } finally {
            try{
                LogRecordRootObject rootObject = builder.build();
                LogRecordEvaluationContext evaluationContext = new LogRecordEvaluationContext(rootObject, discoverer);
                // 是否记录日志
                boolean isRecord = true;
                String conditionSpel = logRecord.condition();
                if (StringUtils.isNoneBlank(conditionSpel)){
                    // 获取触发条件
                    isRecord = parser.parseExpression(conditionSpel).getValue(evaluationContext, Boolean.class);
                }
                if (isRecord){
                    boolean isSuccess = StringUtils.isBlank(rootObject.getErrorMessage());
                    Operator operator;
                    String operatorSpel = logRecord.operator();
                    if (StringUtils.isBlank(operatorSpel)){
                        operator = operatorService.getOperator();
                    }else {
                        operator = parser.parseExpression(operatorSpel).getValue(evaluationContext, Operator.class);
                    }
                    String describe = null;
                    if (isSuccess){
                        String successSpel = logRecord.success();
                        if (StringUtils.isNoneBlank(successSpel)){
                            describe = parser.parseExpression(successSpel).getValue(evaluationContext, String.class);
                        }
                    }else {
                        String failSpel = logRecord.fail();
                        if (StringUtils.isNoneBlank(failSpel)){
                            describe = parser.parseExpression(failSpel).getValue(evaluationContext, String.class);
                        }else {
                            describe = rootObject.getErrorMessage();
                        }
                    }

                    Record.RecordBuilder recordBuilder = Record.builder()
                            .success(isSuccess)
                            .operateType(logRecord.operateType())
                            .operator(operator);
                    if (StringUtils.isNoneBlank(describe)){
                        recordBuilder.describe(describe);
                    }
                    String bizNoSpel = logRecord.bizNo();
                    if (StringUtils.isNoneBlank(bizNoSpel)){
                        String bizNo = parser.parseExpression(bizNoSpel).getValue(evaluationContext, String.class);
                        recordBuilder.bizNo(bizNo);
                    }
                    // 执行记录
                    logRecordService.record(recordBuilder.build());
                }
            }catch (Exception e){
                log.error("记录操作日志异常:{}", e);
            }finally {
                try{
                    // 释放上下文数据
                    LogRecordContext.clear();
                }catch (Exception e){
                    log.error("释放操作日志线程上下文变量异常", e);
                }
            }
        }
    }
}

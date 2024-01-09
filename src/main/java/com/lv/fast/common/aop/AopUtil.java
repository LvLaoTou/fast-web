package com.lv.fast.common.aop;

import cn.hutool.core.util.StrUtil;
import com.lv.fast.common.util.ParameterUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/**
 * @author lvlaotou
 */
public class AopUtil {

    private AopUtil(){}

    /**
     * Spring 参数解析器
     */
    private final static LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    /**
     * spel 表达式解析器
     */
    private final static ExpressionParser parser = new SpelExpressionParser();

    public static <T> T parseExpression(ProceedingJoinPoint joinPoint, String spel, Class<T> target){
        LinkedHashMap<String, Object> params = ParameterUtil.getRequestParam(joinPoint);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        AopRootObject rootObject = AopRootObject.builder()
                .param(params)
                .method(method)
                .build();
        AopEvaluationContext evaluationContext = new AopEvaluationContext(rootObject, discoverer, AopContext.listVariable());
        return parser.parseExpression(spel).getValue(evaluationContext, target);
    }

    public static String parseExpressionIfBlankReturnMethodParam(ProceedingJoinPoint joinPoint, String spel){
        if (StrUtil.isBlank(spel)){
            Object[] parameterValues = joinPoint.getArgs();
            return Arrays.stream(parameterValues).map(Object::toString).collect(Collectors.joining("-"));
        }
        return parseExpression(joinPoint, spel, String.class);
    }

    public static String parseExpressionIfBlankReturnMethodName(ProceedingJoinPoint joinPoint, String spel){
        if (StrUtil.isBlank(spel)){
            Signature signature = joinPoint.getSignature();
            return signature.getDeclaringTypeName()+"#"+signature.getName();
        }
        return parseExpression(joinPoint, spel, String.class);
    }


    @SuppressWarnings("unused")
    public static String parseExpressionIfBlankReturnMethodNameAndParam(ProceedingJoinPoint joinPoint, String spel){
        if (StrUtil.isBlank(spel)){
            Object[] parameterValues = joinPoint.getArgs();
            Signature signature = joinPoint.getSignature();
            String methodName = signature.getDeclaringTypeName()+"#"+signature.getName();
            return methodName + "@" + Arrays.stream(parameterValues).map(Object::toString).collect(Collectors.joining("-"));
        }
        return parseExpression(joinPoint, spel, String.class);
    }
}

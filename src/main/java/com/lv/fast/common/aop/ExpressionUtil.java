package com.lv.fast.common.aop;

import cn.hutool.core.util.StrUtil;
import com.lv.fast.common.util.ParameterUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * SpEL表达式解析工具类
 *
 * @author lvlaotou
 * @since 2026-07-25
 * @generated-by oh-my-pi (qwen3.8-max-preview)
 */
public class ExpressionUtil {

    private ExpressionUtil(){}

    /**
     * Spring 参数解析器
     */
    private final static StandardReflectionParameterNameDiscoverer DISCOVERER = new StandardReflectionParameterNameDiscoverer();

    /**
     * spel 表达式解析器
     */
    private final static ExpressionParser PARSER = new SpelExpressionParser();

    public static <T> T parseExpression(ProceedingJoinPoint joinPoint, String spel, Class<T> target){
        LinkedHashMap<String, Object> params = ParameterUtil.getRequestParam(joinPoint);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        AopRootObject rootObject = AopRootObject.builder()
                .param(params)
                .method(method)
                .build();
        AopEvaluationContext evaluationContext = new AopEvaluationContext(rootObject, DISCOVERER, AopContext.listVariable());
        return PARSER.parseExpression(spel).getValue(evaluationContext, target);
    }

    public static String parseExpressionIfBlankReturnMethodParam(ProceedingJoinPoint joinPoint, String spel){
        if (StrUtil.isBlank(spel)){
            return joinArgs(joinPoint);
        }
        return parseExpression(joinPoint, spel, String.class);
    }

    public static String parseExpressionIfBlankReturnMethodName(ProceedingJoinPoint joinPoint, String spel){
        if (StrUtil.isBlank(spel)){
            return getMethodFullName(joinPoint);
        }
        return parseExpression(joinPoint, spel, String.class);
    }

    public static String parseExpressionIfBlankReturnMethodNameAndParam(ProceedingJoinPoint joinPoint, String spel){
        if (StrUtil.isBlank(spel)){
            return getMethodFullName(joinPoint) + "@" + joinArgs(joinPoint);
        }
        return parseExpression(joinPoint, spel, String.class);
    }

    /**
     * 拼接方法所有入参的字符串形式，以 "-" 连接
     */
    private static String joinArgs(ProceedingJoinPoint joinPoint){
        return Arrays.stream(joinPoint.getArgs()).map(arg -> Objects.toString(arg, "")).collect(Collectors.joining("-"));
    }

    /**
     * 获取方法全限定名，格式：声明类全限定名#方法名
     */
    private static String getMethodFullName(ProceedingJoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        return signature.getDeclaringTypeName() + "#" + signature.getName();
    }
}

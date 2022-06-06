package com.lv.fast.common.aop;

import com.lv.fast.common.util.ParameterUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author jie.lv
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
        Map<String, Object> params = ParameterUtil.getRequestParam(joinPoint);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        AopRootObject rootObject = AopRootObject.builder()
                .param(params)
                .method(method)
                .build();
        AopEvaluationContext evaluationContext = new AopEvaluationContext(rootObject, discoverer, AopContext.listVariable());
        return parser.parseExpression(spel).getValue(evaluationContext, target);
    }
}

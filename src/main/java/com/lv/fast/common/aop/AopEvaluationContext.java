package com.lv.fast.common.aop;

import cn.hutool.core.collection.CollectionUtil;
import com.lv.fast.common.log.LogRecordConstant;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

import java.util.Map;

/**
 * @author lvlaotou
 */
public class AopEvaluationContext extends MethodBasedEvaluationContext {

    public AopEvaluationContext(AopRootObject rootObject, ParameterNameDiscoverer parameterNameDiscoverer, Map<String, Object> variables) {
        //把方法的参数都放到 SpEL 解析的 RootObject 中
        super(rootObject, rootObject.getMethod(), rootObject.getArgs(), parameterNameDiscoverer);
        //把 Context 中的变量都放到 RootObject 中
        if (CollectionUtil.isNotEmpty(variables)){
            variables.forEach(this::setVariable);
        }
        //把方法的返回值和 错误信息 都放到 RootObject 中
        setVariable(LogRecordConstant.METHOD_RESULT_EVALUATION, rootObject.getResult());
        setVariable(LogRecordConstant.ERROR_MESSAGE_EVALUATION, rootObject.getErrorMessage());
    }
}

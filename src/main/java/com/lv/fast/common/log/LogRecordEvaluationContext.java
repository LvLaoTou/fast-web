package com.lv.fast.common.log;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

import java.util.Map;

/**
 * @author jie.lv
 */
public class LogRecordEvaluationContext extends MethodBasedEvaluationContext {

    public LogRecordEvaluationContext(LogRecordRootObject rootObject, ParameterNameDiscoverer parameterNameDiscoverer) {
        //把方法的参数都放到 SpEL 解析的 RootObject 中
        super(rootObject, rootObject.getMethod(), rootObject.getArgs(), parameterNameDiscoverer);
        //把 LogRecordContext 中的变量都放到 RootObject 中
        Map<String, Object> variables = LogRecordContext.listVariable();
        if (CollectionUtil.isNotEmpty(variables)){
            variables.forEach((k,v)->{
                setVariable(k, v);
            });
        }
        //把方法的返回值和 错误信息 都放到 RootObject 中
        setVariable(LogRecordConstant.METHOD_RESULT_EVALUATION, rootObject.getResult());
        setVariable(LogRecordConstant.ERROR_MESSAGE_EVALUATION, rootObject.getErrorMessage());
    }
}

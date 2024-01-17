package com.lv.fast.common.log;

import java.lang.annotation.*;

/**
 * 记录日志注解
 * @author lvlaotou
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LogRecord {

    /**
     * 操作描述
     * 支持spel表达式,spel表达式结果需要为String
     */
    String describe();

    /**
     * 业务号
     * 支持spel表达式,spel表达式结果需要为String
     */
    String bizNo() default "";

    /**
     * 操作类型
     */
    OperateTypeEnum operateType();

    /**
     * 错误信息
     * 支持spel表达式,spel表达式结果需要为String
     * 默认为java.lang.Throwable#getMessage()
     */
    String errorMessage() default "";

    /**
     * 操作者
     * 支持spel表达式,spel表达式结果需要为com.lv.fast.common.log.Operator
     * 默认为com.lv.fast.common.log.OperatorService#getOperator()
     */
    String operator() default "";

    /**
     * 触发条件
     * 支持spel表达式,spel表达式结果需要为boolean
     * 默认为true
     */
    String condition() default "";
}

package com.lv.fast.common.log;

import java.lang.annotation.*;

/**
 * 记录日志注解
 * @author jie.lv
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LogRecord {

    /**
     * 成功模板
     * 支持spel表达式,spel表达式结果需要为String
     */
    String success();

    /**
     * 业务号
     */
    String bizNo();

    /**
     * 操作类型
     */
    OperateTypeEnum operateType();

    /**
     * 失败模板
     * 支持spel表达式,spel表达式结果需要为String
     * 默认为java.lang.Throwable#getMessage()
     */
    String fail() default "";

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

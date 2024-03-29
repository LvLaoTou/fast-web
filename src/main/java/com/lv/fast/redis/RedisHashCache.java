package com.lv.fast.redis;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author lvlaotou
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisHashCache{

    /**
     * redis key
     * hash类型时默认为方法全限定名
     * 支持Spel表达试 返回结果必须为String
     */
    String key() default "";

    /**
     * redis hash key
     * 默认为方法参数
     * 支持Spel表达试 返回结果必须为String
     */
    String hashKey() default "";

    /**
     * 是否查询缓存
     * 支持Spel表达试 返回结果必须为boolean
     */
    String condition() default "";

    /**
     * 是否缓存结果
     * 支持Spel表达试 返回结果必须为boolean
     */
    String unless() default "";

    /**
     * 过期时间 小于等于0表示不设置过期时间
     */
    long timeout() default 24 * 60 * 60;

    /**
     * 过期时间单位
     */
    TimeUnit unit() default TimeUnit.SECONDS;
}

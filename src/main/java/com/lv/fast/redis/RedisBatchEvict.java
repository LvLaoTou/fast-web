package com.lv.fast.redis;

import java.lang.annotation.*;

/**
 * @author lvlaotou
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisBatchEvict {

    RedisEvict[] value() default {};

    /**
     * 支持spel
     * 默认为true
     * 执行条件 优先级低于com.lv.fast.redis.RedisEvict#unless()
     * @return
     */
    String unless() default "";
}

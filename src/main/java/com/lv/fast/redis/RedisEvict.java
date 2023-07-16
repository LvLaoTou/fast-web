package com.lv.fast.redis;

import java.lang.annotation.*;

/**
 * @author lvlaotou
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisEvict {

    /**
     * redis key
     * 默认为方法名
     */
    String key() default "";

    /**
     * 如果为空则直接删除key,如果不为空则删除hash中的指定元素
     */
    String hashKey() default "";

    /**
     * 支持spel
     * 默认为true
     * 执行条件  优先级高于com.lv.fast.redis.RedisBatchEvict#unless()
     */
    String unless() default "";
}

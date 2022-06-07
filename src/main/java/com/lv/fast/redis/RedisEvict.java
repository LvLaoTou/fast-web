package com.lv.fast.redis;

import java.lang.annotation.*;

/**
 * @author jie.lv
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisEvict {

    /**
     * redis key
     * 默认为方法名+参数
     * @return
     */
    String key() default "";

    /**
     * 如果为空则直接删除key,如果不为空则删除hash中的指定元素
     * @return
     */
    String hashKey() default "";

    /**
     * 支持spel
     * 默认为true
     * 执行条件  优先级高于com.lv.fast.redis.RedisBatchEvict#unless()
     * @return
     */
    String unless() default "";
}

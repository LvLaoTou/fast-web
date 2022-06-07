package com.lv.fast.redis;

import cn.hutool.core.util.StrUtil;
import com.lv.fast.common.aop.AopContext;
import com.lv.fast.common.aop.AopUtil;
import com.lv.fast.common.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author jie.lv
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RedisAop {

    private final RedisTemplate redisTemplate;


    @Pointcut("@annotation(redisHashCache)")
    public void hashCachePointcut(RedisHashCache redisHashCache) {

    }

    @SneakyThrows
    @Around("hashCachePointcut(redisHashCache)")
    public Object hashCache(ProceedingJoinPoint joinPoint, RedisHashCache redisHashCache){
        AopContext.initVariableThreadContext();
        try{
            String keySpel = redisHashCache.key();
            String hashKeySpel = redisHashCache.hashKey();
            boolean isQueryCache = true;
            String condition = redisHashCache.condition();
            if (StrUtil.isNotBlank(condition)){
                // 获取触发条件
                isQueryCache = AopUtil.parseExpression(joinPoint, condition, Boolean.class);
            }
            String key = AopUtil.parseExpression(joinPoint, keySpel, String.class);
            String hashKey = AopUtil.parseExpression(joinPoint, hashKeySpel, String.class);
            if (isQueryCache){
                Object redisValue = redisTemplate.opsForHash().get(key, hashKey);
                if (redisValue != null && StrUtil.isNotBlank(redisValue.toString())){
                    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
                    Method method = methodSignature.getMethod();
                    return JsonUtil.toObject(redisValue.toString(), method.getReturnType());
                }
            }
            Object result = joinPoint.proceed();
            boolean canPutCache = true;
            String unless = redisHashCache.unless();
            if (StrUtil.isNotBlank(unless)){
                AopContext.putVariable("result", result);
                canPutCache = AopUtil.parseExpression(joinPoint, unless, Boolean.class);
            }
            if (canPutCache){
                redisTemplate.opsForHash().put(key, hashKey, result);
                long timeout = redisHashCache.timeout();
                if (timeout > 0){
                    redisTemplate.expire(key, timeout, redisHashCache.unit());
                }
            }
            return result;
        }finally {
            AopContext.clearVariableThreadContext();
        }
    }
}

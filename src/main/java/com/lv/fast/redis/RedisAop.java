package com.lv.fast.redis;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Sets;
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
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lvlaotou
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RedisAop {

    private final RedisTemplate<String, ?> redisTemplate;


    @Pointcut("@annotation(redisHashCache)")
    public void hashCachePointcut(RedisHashCache redisHashCache) {

    }

    @Pointcut("@annotation(evict)")
    public void evictPointcut(RedisEvict evict) {

    }

    @Pointcut("@annotation(batchEvict)")
    public void batchEvictPointcut(RedisBatchEvict batchEvict) {

    }

    @SneakyThrows
    @Around(value = "hashCachePointcut(redisHashCache)", argNames = "joinPoint,redisHashCache")
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
            String key = AopUtil.parseExpressionIfBlankReturnMethodName(joinPoint, keySpel);
            String hashKey = AopUtil.parseExpressionIfBlankReturnMethodParam(joinPoint, hashKeySpel);
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

    @SneakyThrows
    @Around(value = "evictPointcut(evict)", argNames = "joinPoint,evict")
    public Object evictCache(ProceedingJoinPoint joinPoint, RedisEvict evict){
        AopContext.initVariableThreadContext();
        try {
            Object result = joinPoint.proceed();
            String unless = evict.unless();
            boolean isEvict = true;
            if (StrUtil.isNotBlank(unless)){
                isEvict = AopUtil.parseExpression(joinPoint, unless, Boolean.class);
            }
            if (isEvict){
                String keySpel = evict.key();
                String hashKeySpel = evict.hashKey();
                String key = AopUtil.parseExpressionIfBlankReturnMethodName(joinPoint, keySpel);
                if (StrUtil.isNotBlank(hashKeySpel)){
                    redisTemplate.opsForHash().delete(key, AopUtil.parseExpression(joinPoint, hashKeySpel, String.class));
                }else {
                    redisTemplate.delete(key);
                }
            }
            return result;
        }finally {
            AopContext.clearVariableThreadContext();
        }
    }

    @SneakyThrows
    @Around(value = "batchEvictPointcut(batchEvict)", argNames = "joinPoint,batchEvict")
    public Object batchEvictCache(ProceedingJoinPoint joinPoint, RedisBatchEvict batchEvict){
        AopContext.initVariableThreadContext();
        try {
            Object result = joinPoint.proceed();
            String batchUnless = batchEvict.unless();
            boolean batchIsEvict = true;
            if (StrUtil.isNotBlank(batchUnless)){
                batchIsEvict = AopUtil.parseExpression(joinPoint, batchUnless, Boolean.class);
            }
            RedisEvict[] value = batchEvict.value();
            boolean finalBatchIsEvict = batchIsEvict;
            Map<String, Set<String>> hashMap = Arrays.stream(value)
                    .filter(evict -> {
                        String hashKey = evict.hashKey();
                        if (StrUtil.isBlank(hashKey)){
                            return false;
                        }
                        String unless = evict.unless();
                        if (StrUtil.isNotBlank(unless)){
                            Boolean isEvict = AopUtil.parseExpression(joinPoint, unless, Boolean.class);
                            if (isEvict != null){
                                return isEvict;
                            }
                        }
                        return finalBatchIsEvict;
                    })
                    .collect(Collectors.toMap(evict-> AopUtil.parseExpressionIfBlankReturnMethodName(joinPoint, evict.key()),
                            evict -> Sets.newHashSet(AopUtil.parseExpression(joinPoint, evict.hashKey(), String.class)),
                            (Set<String> a, Set<String> b) -> {
                                    a.addAll(b);
                                    return a;
                            })
                    );
            if (CollectionUtil.isNotEmpty(hashMap)){
                hashMap.forEach((k,v)->redisTemplate.opsForHash().delete(k, v.toArray()));
            }
            Set<String> keyList = Arrays.stream(value)
                    .filter(evict -> {
                        String hashKey = evict.hashKey();
                        if (StrUtil.isNotBlank(hashKey)){
                            return false;
                        }
                        String unless = evict.unless();
                        if (StrUtil.isNotBlank(unless)){
                            Boolean isEvict = AopUtil.parseExpression(joinPoint, unless, Boolean.class);
                            if (isEvict != null){
                                return isEvict;
                            }
                        }
                        return finalBatchIsEvict;
                    })
                    .map(evict-> AopUtil.parseExpressionIfBlankReturnMethodName(joinPoint, evict.key()))
                    .collect(Collectors.toSet());
            if (CollectionUtil.isNotEmpty(keyList)){
                redisTemplate.delete(keyList);
            }
            return result;
        }finally {
            AopContext.clearVariableThreadContext();
        }
    }
}

package com.lv.fast.module.test.service;

import com.lv.fast.common.aop.AopContext;
import com.lv.fast.common.log.LogRecord;
import com.lv.fast.common.log.OperateTypeEnum;
import com.lv.fast.module.test.dto.TestRequest;
import com.lv.fast.redis.RedisBatchEvict;
import com.lv.fast.redis.RedisEvict;
import com.lv.fast.redis.RedisHashCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author lvlaotou
 */
@Service
@RequiredArgsConstructor
public class TestService {

    private final TestService2 testService2;

    @LogRecord(describe = "'请求id:'+#request.id+',请求姓名:'+#request.name+'方法名:'+#methodName+'请求名称:'+#requestName",
            operateType = OperateTypeEnum.UPDATE,
            bizNo = "T(java.util.UUID).randomUUID()",
            errorMessage = "'执行获取请求姓名失败'",
            condition = "#request.success")
    public String convert(TestRequest request){
        AopContext.putVariable("methodName", "convert");
        testService2.test(request);
        AopContext.putVariable("requestName", "convert");
        return request.getName();
    }

    @RedisHashCache(
            key = "T(com.lv.fast.redis.RedisConstant).SYSTEM_KEY_PREFIX+'test:annotation:redisHashCache'",
            hashKey = "#param",
            condition = "#param > 10",
            unless = "#result > 0",
            timeout = 1,
            unit = TimeUnit.HOURS
    )
    public long testRedisCache(long param){
        System.out.println("执行业务:"+param);
        return param + 1;
    }

    @RedisEvict(
            key = "T(com.lv.fast.redis.RedisConstant).SYSTEM_KEY_PREFIX+'test:annotation:redisHashCache'",
            hashKey = "#param",
            unless = "#param < 20"
    )
    public void testRedisEvict(){
        System.out.println("执行清除缓存业务");
    }

    @RedisBatchEvict(
            value = {
                    @RedisEvict(
                            key = "T(com.lv.fast.redis.RedisConstant).SYSTEM_KEY_PREFIX+'test:annotation:redisBatchEvict'",
                            unless = "#param < 10"
                    ),
                    @RedisEvict(
                            key = "T(com.lv.fast.redis.RedisConstant).SYSTEM_KEY_PREFIX+'test:annotation:redisEvict'"
                    ),
                    @RedisEvict(
                            key = "T(com.lv.fast.redis.RedisConstant).SYSTEM_KEY_PREFIX+'test:annotation:redisHashCache'",
                            hashKey = "#param"
                    ),
                    @RedisEvict(
                            key = "T(com.lv.fast.redis.RedisConstant).SYSTEM_KEY_PREFIX+'test:annotation:redisHashCache'",
                            hashKey = "#param + 1",
                            unless = "#param == 15"
                    ),
                    @RedisEvict(
                            key = "T(com.lv.fast.redis.RedisConstant).SYSTEM_KEY_PREFIX+'test:annotation:redisHashCache'",
                            hashKey = "#param - 1"
                    )
            },
            unless = "#param > 10"
    )
    public void testRedisBatchEvict(){
        System.out.print("执行批量清除缓存业务");
    }
}

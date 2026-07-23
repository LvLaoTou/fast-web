package com.lv.fast.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author lvlaotou
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        //关闭事务
        redisTemplate.setEnableTransactionSupport(false);
        //使用JacksonJsonRedisSerializer来序列化和反序列化redis的value值 (Spring Boot 4 默认 Jackson 3)
        JacksonJsonRedisSerializer<?> jsonSerializer = new JacksonJsonRedisSerializer<>(Object.class);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jsonSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jsonSerializer);
        return redisTemplate;
    }

    /**
     * 缓存管理器 (Spring Boot 4 模块化后 @EnableCaching 不再自动配置 RedisCacheManager，需显式声明)
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
        JacksonJsonRedisSerializer<?> jsonSerializer = new JacksonJsonRedisSerializer<>(Object.class);
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer));
        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }
}

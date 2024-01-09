package com.lv.fast.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.lv.fast.common.constant.DateTimeConstant;
import com.lv.fast.common.entity.EnumInterface;
import com.lv.fast.common.util.EnumUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * webmvc配置类
 * @author lvlaotou
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final Jackson2ObjectMapperBuilder builder;

    /**
     * get请求对枚举参数的支持
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new ConverterFactory<String, Enum<? extends EnumInterface<String>>>() {
            @Override
            public <T extends Enum<? extends EnumInterface<String>>> Converter<String, T> getConverter(Class<T> aClass) {
                return source -> EnumUtil.getEnumByCode(aClass, source);
            }
        });
        registry.addConverterFactory(new ConverterFactory<Number, Enum<? extends EnumInterface<Number>>>() {
            @Override
            public <T extends Enum<? extends EnumInterface<Number>>> Converter<Number, T> getConverter(Class<T> aClass) {
                return source -> EnumUtil.getEnumByCode(aClass, source);
            }
        });
    }

    /**
     * 配置跨域请求
     * @param registry CorsRegistry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //添加映射路径
        registry.addMapping("/**")
                //放行哪些原始域
                .allowedOrigins("*")
                //放行哪些原始域(请求方式)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                //该响应的有效时间默认为30分钟，在有效时间内，浏览器无须为同一请求再次发起预检请求
                .maxAge(3600);
    }

    /**
     * 配置SpringBoot Jackson
     * @return MappingJackson2HttpMessageConverter
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeConstant.FORMATTER));
        builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeConstant.FORMATTER));
        builder.serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DateTimeConstant.DATE_FORMAT)));
        builder.deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DateTimeConstant.DATE_FORMAT)));
        builder.serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DateTimeConstant.TIME_FORMAT)));
        builder.deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DateTimeConstant.TIME_FORMAT)));
        //返回long字段 转换为String 因为js中得数字类型不能包含所有的java long值
        builder.serializerByType(Long.class, ToStringSerializer.instance);
        builder.serializerByType(Long.TYPE, ToStringSerializer.instance);
        //配置枚举转换器
        ObjectMapper objectMapper = builder.build();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    /**
     * LocalDateTime转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    @ConditionalOnBean(name = "requestMappingHandlerAdapter")
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return source -> LocalDateTime.parse(source, DateTimeFormatter.ofPattern(DateTimeConstant.DATE_TIME_FORMAT));
    }

    /**
     * LocalDate转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    @ConditionalOnBean(name = "requestMappingHandlerAdapter")
    public Converter<String, LocalDate> localDateConverter() {
        return source -> LocalDate.parse(source,  DateTimeFormatter.ofPattern(DateTimeConstant.DATE_FORMAT));
    }

    /**
     * LocalTime转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    @ConditionalOnBean(name = "requestMappingHandlerAdapter")
    public Converter<String, LocalTime> localTimeConverter() {
        return source -> LocalTime.parse(source,  DateTimeFormatter.ofPattern(DateTimeConstant.TIME_FORMAT));
    }
}

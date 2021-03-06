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
import com.lv.fast.common.valid.Code;
import lombok.AllArgsConstructor;
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
import java.util.concurrent.ConcurrentHashMap;

/**
 * webmvc配置类
 * @author lv
 */
@Configuration
@AllArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final Jackson2ObjectMapperBuilder builder;

    /**
     * get请求对枚举参数的支持
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new ConverterFactory<String, Enum<? extends Code<String>>>() {
            private final ConcurrentHashMap<Class, EnumConverter> CONCURRENT_MAP = new ConcurrentHashMap<>();
            @Override
            public <T extends Enum<? extends Code<String>>> Converter<String, T> getConverter(Class<T> aClass) {
                EnumConverter<String, T> enumConverter = CONCURRENT_MAP.get(aClass);
                if (enumConverter == null){
                    enumConverter = new EnumConverter<>(aClass);
                    CONCURRENT_MAP.put(aClass, enumConverter);
                }
                return enumConverter;
            }
        });
        registry.addConverterFactory(new ConverterFactory<Integer, Enum<? extends Code<Integer>>>() {
            private final ConcurrentHashMap<Class, EnumConverter> CONCURRENT_MAP = new ConcurrentHashMap<>();
            @Override
            public <T extends Enum<? extends Code<Integer>>> Converter<Integer, T> getConverter(Class<T> aClass) {
                EnumConverter<Integer, T> enumConverter = CONCURRENT_MAP.get(aClass);
                if (enumConverter == null){
                    enumConverter = new EnumConverter<>(aClass);
                    CONCURRENT_MAP.put(aClass, enumConverter);
                }
                return enumConverter;
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
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(DateTimeConstant.DATE_TIME_FORMAT));
            }
        };
    }

    /**
     * LocalDate转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                return LocalDate.parse(source,  DateTimeFormatter.ofPattern(DateTimeConstant.DATE_FORMAT));
            }
        };
    }

    /**
     * LocalTime转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, LocalTime> localTimeConverter() {
        return new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(String source) {
                return LocalTime.parse(source,  DateTimeFormatter.ofPattern(DateTimeConstant.TIME_FORMAT));
            }
        };
    }
}

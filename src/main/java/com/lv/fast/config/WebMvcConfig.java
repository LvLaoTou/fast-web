package com.lv.fast.config;

import com.lv.fast.common.constant.DateTimeConstant;
import com.lv.fast.common.entity.EnumInterface;
import com.lv.fast.common.util.EnumUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tools.jackson.databind.ext.javatime.deser.LocalDateDeserializer;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.deser.LocalTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateSerializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import tools.jackson.databind.ext.javatime.ser.LocalTimeSerializer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.ser.std.ToStringSerializer;

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
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 增加knife4j ui路径处理器
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
    }

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
     * 配置SpringBoot Jackson (Spring Boot 4 默认 Jackson 3，手动构建消息转换器)
     * @return JacksonJsonHttpMessageConverter
     */
    @Bean
    public JacksonJsonHttpMessageConverter mappingJackson2HttpMessageConverter() {
        // 自定义 java.time 序列化/反序列化格式
        SimpleModule timeModule = new SimpleModule();
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeConstant.FORMATTER));
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeConstant.FORMATTER));
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DateTimeConstant.DATE_FORMAT)));
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DateTimeConstant.DATE_FORMAT)));
        timeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DateTimeConstant.TIME_FORMAT)));
        timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DateTimeConstant.TIME_FORMAT)));
        //返回long字段 转换为String 因为js中的数字类型不能包含所有的java long值
        SimpleModule longModule = new SimpleModule();
        longModule.addSerializer(Long.class, ToStringSerializer.instance);
        longModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        JsonMapper jsonMapper = JsonMapper.builder()
                .addModule(timeModule)
                .addModule(longModule)
                .build();
        JacksonJsonHttpMessageConverter converter = new JacksonJsonHttpMessageConverter(jsonMapper);
        converter.setDefaultCharset(StandardCharsets.UTF_8);
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

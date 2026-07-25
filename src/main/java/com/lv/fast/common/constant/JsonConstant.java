package com.lv.fast.common.constant;

import com.lv.fast.config.EnumInterfaceJackson2Module;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Json常量类
 * @author lvlaotou
 */
public class JsonConstant {

    private JsonConstant(){}

    /**
     * 序列化
     * 不建议直接使用, 建议使用包装工具类方法{@link com.lv.fast.common.util.JsonUtil#toJson(Object)}  }
     */
    public static final ObjectMapper WRITE_MAPPER = getObjectMapper();

    /**
     * 反序列化
     * 不建议直接使用, 建议使用包装工具类方法{@link com.lv.fast.common.util.JsonUtil#toObject(String, Class)}
     */
    public static final JsonMapper READ_MAPPER = getJsonMapper();

    private static ObjectMapper getObjectMapper(){
        return JsonMapper.builder()
                .defaultDateFormat(new SimpleDateFormat(DateTimeConstant.DATE_TIME_FORMAT))
                .addModule(getTimeModule())
                .addModule(new EnumInterfaceJackson2Module())
                .defaultPropertyInclusion(JsonInclude.Value.ALL_ALWAYS)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .build();
    }

    private static JsonMapper getJsonMapper(){
        return JsonMapper.builder()
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .defaultDateFormat(new SimpleDateFormat(DateTimeConstant.DATE_TIME_FORMAT))
                .addModule(getTimeModule())
                .addModule(new EnumInterfaceJackson2Module())
                .defaultPropertyInclusion(JsonInclude.Value.ALL_ALWAYS)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .build();
    }

    private static JavaTimeModule getTimeModule(){
        // 初始化JavaTimeModule
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        //处理LocalDateTime
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeConstant.FORMATTER));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeConstant.FORMATTER));
        //处理LocalDate
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DateTimeConstant.DATE_FORMAT);
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        //处理LocalTime
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(DateTimeConstant.TIME_FORMAT);
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
        return javaTimeModule;
    }
}

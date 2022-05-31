package com.lv.fast.common.constant;

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
 * @author jie.lv
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
        ObjectMapper objectMapper = new ObjectMapper();
        //设置java.util.Date时间类的序列化以及反序列化的格式
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeConstant.DATE_TIME_FORMAT));
        //注册时间模块
        objectMapper.registerModule(getTimeModule());
        // 包含所有字段
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 在序列化一个空对象时时不抛出异常
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 忽略反序列化时在json字符串中存在, 但在java对象中不存在的属性
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return objectMapper;
    }

    private static JsonMapper getJsonMapper(){
        // 忽略大小写
        JsonMapper jsonMapper = JsonMapper.builder()
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .build();
        jsonMapper.registerModule(getTimeModule());
        jsonMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        jsonMapper.setDateFormat(new SimpleDateFormat(DateTimeConstant.DATE_TIME_FORMAT));
        // 在序列化一个空对象时时不抛出异常
        jsonMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 忽略反序列化时在json字符串中存在, 但在java对象中不存在的属性
        jsonMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        jsonMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return jsonMapper;
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

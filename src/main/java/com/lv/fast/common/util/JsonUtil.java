package com.lv.fast.common.util;

import cn.hutool.core.util.StrUtil;
import com.lv.fast.common.constant.JsonConstant;
import lombok.SneakyThrows;

/**
 * JSON序列化/反序列化工具类
 *
 * @author lvlaotou
 * @since 2026-07-25
 * @generated-by oh-my-pi (qwen3.8-max-preview)
 */
public class JsonUtil {

    private JsonUtil(){}

    /**
     * 对象序列化为JSON字符串
     */
    @SneakyThrows
    public static String toJson(Object value){
        return JsonConstant.WRITE_MAPPER.writeValueAsString(value);
    }

    /**
     * JSON字符串反序列化为对象
     */
    @SneakyThrows
    public static <T> T toObject(String json, Class<T> target){
        return JsonConstant.READ_MAPPER.readValue(json, target);
    }

    /**
     * JSON字符串反序列化为对象，JSON为空时返回null
     */
    @SneakyThrows
    public static <T> T toObjectAllowNull(String json, Class<T> target){
        if (StrUtil.isBlank(json)){
            return null;
        }
        return JsonConstant.READ_MAPPER.readValue(json, target);
    }
}

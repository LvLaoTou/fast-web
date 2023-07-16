package com.lv.fast.common.util;

import cn.hutool.core.util.StrUtil;
import com.lv.fast.common.constant.JsonConstant;
import lombok.SneakyThrows;

/**
 * @author lvlaotou
 */
public class JsonUtil {

    private JsonUtil(){}

    @SneakyThrows
    @SuppressWarnings("unused")
    public static String toJson(Object value){
        return JsonConstant.WRITE_MAPPER.writeValueAsString(value);
    }

    @SneakyThrows
    @SuppressWarnings("unused")
    public static <T> T toObject(String json, Class<T> target){
        return JsonConstant.READ_MAPPER.readValue(json, target);
    }

    @SneakyThrows
    @SuppressWarnings("unused")
    public static <T> T toObjectAllowNull(String json, Class<T> target){
        if (StrUtil.isBlank(json)){
            return null;
        }
        return JsonConstant.READ_MAPPER.readValue(json, target);
    }
}

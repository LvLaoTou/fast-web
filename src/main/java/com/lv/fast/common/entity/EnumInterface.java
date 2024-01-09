package com.lv.fast.common.entity;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 枚举 统一接口
 * @author lvlaotou
 */
public interface EnumInterface<T> extends Code<T>{

    @JsonValue
    @Override
    T getCode();

    /**
     * 无法匹配枚举项时提示的错误描述
     * @return 错误描述
     */
    default String errorDescribe(){
        return "无效枚举";
    }
}

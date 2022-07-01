package com.lv.fast.common.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public interface EnumInterface<T> extends Code<T>{

    @JsonValue
    @Override
    T getCode();
}

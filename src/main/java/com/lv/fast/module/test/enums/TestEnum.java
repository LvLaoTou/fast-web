package com.lv.fast.module.test.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.lv.fast.common.entity.EnumInterface;
import com.lv.fast.common.util.EnumUtil;

/**
 * 测试 枚举
 * @author lvlaotou
 */

public enum TestEnum implements EnumInterface<Integer> {

    ONE(1),

    TWO(2),

    ;

    @EnumValue
    private final int code;

    TestEnum(int code){
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String errorDescribe(){
        return "无效测试枚举";
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TestEnum match(Object code){
        return EnumUtil.getEnumByCode(TestEnum.class, code);
    }
}

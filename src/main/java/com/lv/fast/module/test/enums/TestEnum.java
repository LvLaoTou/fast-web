package com.lv.fast.module.test.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lv.fast.common.entity.EnumInterface;

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

}

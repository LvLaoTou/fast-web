package com.lv.fast.common.enums;


import com.lv.fast.common.EnumValid;

/**
 * 排序方式
 * @author lv
 * @version 1.0.0
 */
public enum OrderTypeEnum implements EnumValid {

    /**
     * 升序
     */
    ASC("asc"),

    /**
     * 降序
     */
    DESC("desc")
    ;

    private final String code;

    OrderTypeEnum(String code){
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }
}

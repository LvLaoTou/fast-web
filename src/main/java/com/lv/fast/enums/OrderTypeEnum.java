package com.lv.fast.enums;


import com.lv.fast.common.Code;

/**
 * 排序方式
 * @author lv
 * @version 1.0.0
 */
public enum OrderTypeEnum implements Code {

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

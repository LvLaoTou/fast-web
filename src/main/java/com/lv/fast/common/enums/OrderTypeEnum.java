package com.lv.fast.common.enums;


import com.lv.fast.common.entity.EnumInterface;

/**
 * 排序方式
 * @author lvlaotou
 */
public enum OrderTypeEnum implements EnumInterface<String> {

    /**
     * 升序
     */
    ASC,

    /**
     * 降序
     */
    DESC
    ;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String errorDescribe() {
        return "无效排序方式";
    }


}

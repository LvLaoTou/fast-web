package com.lv.fast.common.enums;


import com.lv.fast.common.valid.Code;

/**
 * 排序方式
 * @author lv
 * @version 1.0.0
 */
public enum OrderTypeEnum implements Code<String> {

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
}

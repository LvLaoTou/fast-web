package com.lv.fast.common.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.lv.fast.common.entity.Code;
import com.lv.fast.common.util.EnumUtil;

/**
 * 排序方式
 * @author lvlaotou
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

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static OrderTypeEnum match(Object code){
        return EnumUtil.getEnumByCode(OrderTypeEnum.class, code, "无效排序方式");
    }
}

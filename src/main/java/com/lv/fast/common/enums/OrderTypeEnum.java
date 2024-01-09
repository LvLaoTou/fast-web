package com.lv.fast.common.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.lv.fast.common.entity.EnumInterface;
import com.lv.fast.common.util.EnumUtil;

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


    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static OrderTypeEnum match(Object code){
        return EnumUtil.getEnumByCode(OrderTypeEnum.class, code);
    }
}

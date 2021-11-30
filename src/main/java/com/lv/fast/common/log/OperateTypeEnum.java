package com.lv.fast.common.log;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lv.fast.common.util.EnumUtil;
import com.lv.fast.common.valid.Code;

/**
 * 操作类型枚举类
 * @author lv
 * @version 1.0.0
 */
public enum OperateTypeEnum implements Code<Integer> {

    /**
     * 删除
     */
    DELETE(1),

    /**
     * 修改
     */
    UPDATE(2),

    /**
     * 查询
     */
    SELECT(3),

    /**
     * 新增
     */
    INSERT(4)
    ;

    @EnumValue
    @JsonValue
    private int code;

    OperateTypeEnum(int code){
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @JsonCreator
    public static OperateTypeEnum create(int code){
        return EnumUtil.getEnumByCode(OperateTypeEnum.class, code, "无效的操作类型");
    }
}

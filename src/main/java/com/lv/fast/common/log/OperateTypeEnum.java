package com.lv.fast.common.log;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lv.fast.common.entity.EnumInterface;
import com.lv.fast.common.util.EnumUtil;

/**
 * 操作类型枚举类
 * @author lv
 */
public enum OperateTypeEnum implements EnumInterface<Integer> {

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
    private final int code;

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

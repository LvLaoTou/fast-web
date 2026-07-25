package com.lv.fast.common.log;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lv.fast.common.entity.EnumInterface;

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
    private final int code;

    OperateTypeEnum(int code){
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String errorDescribe() {
        return "无效操作类型";
    }
}

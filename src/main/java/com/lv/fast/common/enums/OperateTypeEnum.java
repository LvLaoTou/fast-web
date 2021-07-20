package com.lv.fast.common.enums;

import com.lv.fast.common.valid.IntegerCode;

/**
 * 操作类型枚举类
 * @author lv
 * @version 1.0.0
 */
public enum OperateTypeEnum implements IntegerCode {

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

    private int code;

    OperateTypeEnum(int code){
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}

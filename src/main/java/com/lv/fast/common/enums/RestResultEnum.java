package com.lv.fast.common.enums;

import com.lv.fast.common.entity.Describe;
import com.lv.fast.common.constant.RestResultCodeConstant;
import com.lv.fast.common.constant.RestResultDescribeConstant;

/**
 *  全局统一响应枚举对象
 * @author lvlaotou
 */
public enum RestResultEnum implements Describe<String> {

    /** 成功 */
    SUCCESS(RestResultCodeConstant.SUCCESS, RestResultDescribeConstant.SUCCESS),

    /** 未知错误 */
    UNKNOWN_ERROR(RestResultCodeConstant.UNKNOWN, RestResultDescribeConstant.UNKNOWN),

    /** 参数错误 */
    PARAM_ERROR(RestResultCodeConstant.PARAM_ERROR, RestResultDescribeConstant.PARAM_ERROR),

    /** 数据库已存在 */
    DATABASE_EXIST_ERROR(RestResultCodeConstant.DATABASE_EXIST, RestResultDescribeConstant.DATABASE_EXIST),

    /** 数据库不存在 */
    DATABASE_NOT_EXIST_ERROR(RestResultCodeConstant.DATABASE_NOT_EXIST, RestResultDescribeConstant.DATABASE_NOT_EXIST),

    /** 请求路径不存在 */
    PATH_NOT_FOUND(RestResultCodeConstant.PATH_NOT_FOUND, RestResultDescribeConstant.PATH_NOT_FOUND);

    /**
     * 标识码
     */
    private final String code;

    /**
     * 描述
     */
    private final String describe;

    RestResultEnum(String code, String describe){
        this.code = code;
        this.describe = describe;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDescribe() {
        return this.describe;
    }
}

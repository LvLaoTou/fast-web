package com.lv.fast.enums;

import com.lv.fast.common.Describe;
import com.lv.fast.constant.RestResultCodeConstant;
import com.lv.fast.constant.RestResultDescribeConstant;

/**
 * @Description 全局统一响应枚举对象
 * @Author jie.lv
 */
public enum RestResultEnum implements Describe {

    /** 成功 */
    SUCCESS(RestResultCodeConstant.SUCCESS, RestResultDescribeConstant.SUCCESS),

    /** 未知错误 */
    UNKNOWN_ERROR(RestResultCodeConstant.UNKNOWN, RestResultDescribeConstant.UNKNOWN),

    /** 参数错误 */
    PARAM_ERROR(RestResultCodeConstant.PARAM_ERROR, RestResultDescribeConstant.PARAM_ERROR),

    /** 数据库已存在 */
    DATABASE_EXIST_ERROR(RestResultCodeConstant.DATABASE_EXIST, RestResultDescribeConstant.DATABASE_EXIST),

    /** 权限不足 */
    AUTHORIZATION_INSUFFICIENT_ERROR(RestResultCodeConstant.AUTHORIZATION_INSUFFICIENT, RestResultDescribeConstant.AUTHORIZATION_INSUFFICIENT),

    /** 没有对应的处理器 */
    PATH_NOT_FOUND(RestResultCodeConstant.PATH_NO_HANDLER, RestResultDescribeConstant.PATH_NO_HANDLER),

    /** token为空错误 */
    TOKEN_IS_NULL(RestResultCodeConstant.AUTHORIZATION_TOKEN_IS_NULL, RestResultDescribeConstant.AUTHORIZATION_TOKEN_IS_NULL),

    /** token已失效 */
    TOKEN_INVALID(RestResultCodeConstant.AUTHORIZATION_TOKEN_INVALID, RestResultDescribeConstant.AUTHORIZATION_TOKEN_INVALID),
    ;

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
        return null;
    }

    @Override
    public String getDescribe() {
        return null;
    }
}

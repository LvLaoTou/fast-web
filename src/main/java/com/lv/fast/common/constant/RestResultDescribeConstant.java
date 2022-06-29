package com.lv.fast.common.constant;

/**
 * 全局响应描述常量类
 * @author lvlaotou
 */
public class RestResultDescribeConstant {


    /**
     * 常量类私有无参构造
     */
    private RestResultDescribeConstant(){}

    /** 成功 */
    public static final String SUCCESS = "success";

    /** 未知错误 */
    public static final String UNKNOWN = "未知异常";

    /** 参数错误 */
    public static final String PARAM_ERROR = "参数错误";

    /** 数据库记录不存在 */
    public static final String DATABASE_EXIST = "数据库中该记录已存在";

    /** 数据库记录已存在 */
    public static final String DATABASE_NOT_EXIST = "数据库中该记录不存在";

    /** 请求地址不存在 */
    public static final String PATH_NOT_FOUND = "请求地址不存在";
}

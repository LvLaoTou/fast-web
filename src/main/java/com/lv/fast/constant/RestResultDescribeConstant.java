package com.lv.fast.constant;

/**
 * 全局响应描述常量类
 * @author lvlaotou
 */
public class RestResultDescribeConstant {

    /**
     * 常量类私有无参构造
     */
    private RestResultDescribeConstant(){}

    /**
     * 成功
     */
    public static final String SUCCESS = "success";

    /**
     * 错误
     */
    public static final String UNKNOWN = "未知异常";

    /**
     * 参数模块---参数错误
     */
    public static final String PARAM_ERROR = "参数错误";

    /**
     * 数据库模块---记录不存在
     */
    public static final String DATABASE_EXIST = "数据库中该记录已存在";

    /**
     * 数据库模块---记录已存在
     */
    public static final String DATABASE_NOT_EXIST = "数据库中该记录不存在";

    /**
     * 权限模块---权限不足
     */
    public static final String AUTHORIZATION_INSUFFICIENT = "权限不足";

    /**
     * 权限模块---token为空
     */
    public static final String AUTHORIZATION_TOKEN_IS_NULL = "token不能为空";

    /**
     * 权限模块---token已失效
     */
    public static final String AUTHORIZATION_TOKEN_INVALID = "token已失效";

    /**
     * 路径模块---路径不存在错误
     */
    public static final String PATH_NO_HANDLER = "路径不存在";

    /**
     * 用户模块---用户名/密码错误
     */
    public static final String LOGIN_FAIL = "用户名或密码错误";

    /**
     * 用户模块---账户被锁定
     */
    public static final String LOGIN_USER_LOCK = "用户被锁定";
}

package com.lv.fast.common.constant;

/**
 * @Description 全局响应标识码常量类
 * @Author jie.lv
 */
public class RestResultCodeConstant {

    /**
     * 常量类私有无参构造
     */
    private RestResultCodeConstant(){}

    /*
        状态码设计规约 组成规则： 错误来源标识-模块-序号
        错误来源标识：A--->用户操作错误    B--->服务器错误    C--->外部系统错误
        模块：两位数 01-99  00保留
        序号：两位数 01-99  00保留
        成功：00000
     */

    /**
     * 成功
     */
    public static final String SUCCESS = "00000";

    /**
     * 服务端未知错误
     */
    public static final String UNKNOWN = "B0000";

    /*---------------------------------------------用户输入错误-------------------------------------------------------------*/

    /**
     * 参数模块---参数错误
     */
    public static final String PARAM_ERROR = "A0101";

    /**
     * 数据库模块---记录已存在
     */
    public static final String DATABASE_EXIST = "A0201";

    /**
     * 状态码--数据库模块---记录不存在
     */
    public static final String DATABASE_NOT_EXIST = "A0202";

    /**
     * 权限模块---权限不足
     */
    public static final String AUTHORIZATION_INSUFFICIENT = "A0301";

    /**
     * 权限模块---token为空
     */
    public static final String AUTHORIZATION_TOKEN_IS_NULL = "A0302";

    /**
     * 权限模块---token已失效
     */
    public static final String AUTHORIZATION_TOKEN_INVALID = "A0303";

    /**
     * 路径模块---没有路径处理器
     */
    public static final String PATH_NO_HANDLER = "A0401";

    /**
     * 用户模块---用户名/密码错误
     */
    public static final String LOGIN_FAIL = "A0501";

    /**
     * 用户模块---用户被锁定
     */
    public static final String LOGIN_USER_LOCK = "A0502";
}

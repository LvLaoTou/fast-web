package com.lv.fast.constant;

/**
 * http常量类
 * @author jie.lv
 */
public class HttpConstant {

    private HttpConstant(){}

    /**
     * 后台管理模块  token请求头key
     */
    public static final String TOKEN_HEADER_KEY_SYS = "token";

    /**
     * 跨域请求方式
     */
    public static final String REQUEST_CORS_METHOD = "options";

    /**
     * 在request存放错误信息的Attribute的key
     */
    public static final String REQUEST_ATTRIBUTE_ERROR_MESSAGE_KEY = "message";

    /**
     * 在request存放错误码的Attribute的key
     */
    public static final String REQUEST_ATTRIBUTE_ERROR_CODE_KEY = "code";
}

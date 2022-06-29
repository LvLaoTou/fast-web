package com.lv.fast.common.constant;

/**
 *  全局响应标识码常量类
 * @author lvlaotou
 */
public class RestResultCodeConstant {

    /**
     * 常量类私有无参构造
     */
    private RestResultCodeConstant(){}

    /*
        状态码设计规约 组成规则： 错误来源标识+模块+序号
        错误来源标识：1--->用户操作错误    2--->服务器错误    3--->外部系统错误
        模块：两位数 01-99  00保留
        序号：两位数 01-99  00保留
        成功：0
     */

    /**
     * 成功
     */
    public static final String SUCCESS = "0";

    /**
     * 服务端未知错误
     */
    public static final String UNKNOWN = "20000";

    /*---------------------------------------------用户输入错误-------------------------------------------------------------*/

    /**
     * 参数模块---参数错误
     */
    public static final String PARAM_ERROR = "10101";

    /**
     * 数据库模块---记录已存在
     */
    public static final String DATABASE_EXIST = "10201";

    /**
     * 数据库模块---记录不存在
     */
    public static final String DATABASE_NOT_EXIST = "10202";
}

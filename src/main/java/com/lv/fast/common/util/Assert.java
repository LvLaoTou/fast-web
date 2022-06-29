package com.lv.fast.common.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.lv.fast.common.constant.RestResultCodeConstant;
import com.lv.fast.common.entity.Describe;
import com.lv.fast.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义断言
 * @author lvlaotou
 */
@Slf4j
public class Assert {

    private Assert(){}

    /******************************************************************Assert Object Not Empty***********************************************************************/

    /**
     * 断言不为空，为空则抛出异常
     * @param target 目标对象
     * @param message 错误描述信息
     */
    public static void notEmpty(Object target, String message){
        notEmpty(target, RestResultCodeConstant.PARAM_ERROR, message);
    }

    /**
     * 断言不为空，为空则抛出异常
     * @param target 目标对象
     * @param describe 错误信息
     */
    public static void notEmpty(Object target, Describe describe){
        notEmpty(target, describe.getCode().toString(), describe.getDescribe());
    }

    /**
     * 断言不为空，为空则抛出异常
     * @param target 目标对象
     * @param code 错误状态码
     * @param message 错误描述信息
     */
    public static void notEmpty(Object target, String code, String message){
        isTrue(ObjectUtil.isNotEmpty(target), code, message);
    }

    /******************************************************************Assert String Not Blank***********************************************************************/

    /**
     * 断言字符串不为空
     * @param target 检测目标
     * @param code 错误码
     * @param message 错误描述
     */
    public static void notBlank(String target, String code, String message){
        isTrue(StrUtil.isNotBlank(target), code, message);
    }

    /**
     * 断言字符串不能为空
     * @param target 检测对象
     * @param message 错误描述
     */
    public static void notBlank(String target, String message){
        notBlank(target, RestResultCodeConstant.PARAM_ERROR, message);
    }

    /**
     * 断言字符串不能为空
     * @param target 检测对象
     * @param describe 错误信息
     */
    public static void notBlank(String target, Describe describe){
        notBlank(target, describe.getCode().toString(), describe.getDescribe());
    }

    /******************************************************************Assert Boolean Is True***********************************************************************/

    /**
     * 断言为true  如果为false 抛出自定义异常
     * @param flag 条件
     * @param message 错误描述
     */
    public static void isTrue(boolean flag, String message){
        isTrue(flag, RestResultCodeConstant.PARAM_ERROR, message);
    }

    /**
     * 断言为true  如果为false 抛出自定义异常
     * @param flag 条件
     * @param describe 错误信息
     */
    public static void isTrue(boolean flag, Describe describe){
        isTrue(flag, describe.getCode().toString(), describe.getDescribe());
    }

    /**
     * 断言为true  如果为false 抛出自定义异常
     * @param flag 条件
     * @param message 错误描述
     */
    public static void isTrue(boolean flag, String code, String message){
        if (!flag){
            throw new BusinessException(code, message);
        }
    }

    /******************************************************************Assert Boolean Is False***********************************************************************/

    /**
     * 断言为false  如果为true 抛出自定义异常
     * @param flag 条件
     * @param message 错误描述
     */
    public static void isFalse(boolean flag, String message){
        isFalse(flag, RestResultCodeConstant.PARAM_ERROR, message);
    }

    /**
     * 断言为false  如果为true 抛出自定义异常
     * @param flag 条件
     * @param describe 错误信息
     */
    public static void isFalse(boolean flag, Describe describe){
        isFalse(flag, describe.getCode().toString(), describe.getDescribe());
    }

    /**
     * 断言为false  如果为true 抛出自定义异常
     * @param flag 条件
     * @param message 错误描述
     */
    public static void isFalse(boolean flag, String code, String message){
        if (flag){
            throw new BusinessException(code, message);
        }
    }
}

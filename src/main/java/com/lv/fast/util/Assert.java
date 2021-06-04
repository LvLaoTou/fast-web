package com.lv.fast.util;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lv.fast.constant.RestResultCodeConstant;
import com.lv.fast.enums.RestResultEnum;
import com.lv.fast.exception.MyException;

import java.util.Collection;

/**
 * 自定义断言
 * @author lvlaotou
 * @version 1.0.0
 */
public class Assert {

    private Assert(){}

    /**
     * 断言是否不为空，为空则抛出异常
     * @param target 目标对象
     * @param message 错误描述信息
     */
    public static void assertNotNull(Object target,String message){
        assertNotNull(target, RestResultCodeConstant.PARAM_ERROR, message);
    }

    /**
     * 断言是否不为空，为空则抛出异常
     * @param target 目标对象
     * @param code 错误状态码
     * @param message 错误描述信息
     */
    public static void assertNotNull(Object target, String code, String message){
        if (target == null){
            throw new MyException(code, message);
        }
    }

    /**
     * 断言是否不为空，为空则抛出异常
     * @param target 目标对象
     * @param restResultEnum 响应枚举对象
     */
    public static void assertNotNull(Object target, RestResultEnum restResultEnum){
        assertNotNull(target, restResultEnum.getCode(), restResultEnum.getDescribe());
    }

    /**
     * 断言是否不为空，为空则抛出异常
     * @param target 目标对象
     * @param message 错误描述信息
     */
    public static void assertNotNull(String target,String message){
        assertNotNull(target, RestResultCodeConstant.PARAM_ERROR, message);
    }

    /**
     * 断言是否不为空，为空则抛出异常
     * @param target 目标对象
     * @param code 错误状态码
     * @param message 错误描述信息
     */
    public static void assertNotNull(String target, String code, String message){
        if (StringUtils.isBlank(target)){
            throw new MyException(code, message);
        }
    }

    /**
     * 断言是否不为空，为空则抛出异常
     * @param target 目标对象
     * @param restResultEnum 响应枚举对象
     */
    public static void assertNotNull(String target, RestResultEnum restResultEnum){
        assertNotNull(target, restResultEnum.getCode(), restResultEnum.getDescribe());
    }

    /**
     * 断言不为空  为空则抛出自定义异常
     * @param target 目标集合
     * @param message 描述信息
     */
    public static void assertNotNull(Collection<?> target, String message){
        if (CollectionUtil.isEmpty(target)){
            throw new MyException(RestResultCodeConstant.PARAM_ERROR, message);
        }
    }

    /**
     * 断言是否不为空，为空则抛出异常
     * @param target 目标对象
     * @param message 错误描述信息
     */
    public static void assertNotNull(Object[] target,String message){
        if (target == null || target.length == 0){
            throw new MyException(RestResultCodeConstant.PARAM_ERROR, message);
        }
    }

    /**
     * 断言是否正确
     * @param flag 条件
     * @param message 错误描述
     */
    public static void assertIsTrue(boolean flag,String message){
        assertIsTrue(flag, RestResultCodeConstant.PARAM_ERROR, message);
    }

    /**
     * 断言是否正确
     * @param flag 条件
     * @param message 错误描述
     */
    public static void assertIsTrue(boolean flag, String code, String message){
        if (flag){
            throw new MyException(code, message);
        }
    }
    /**
     * 断言是否正确
     * @param flag 条件
     * @param restResultEnum 响应枚举对象
     */
    public static void assertIsTrue(boolean flag, RestResultEnum restResultEnum){
        assertIsTrue(flag, restResultEnum.getCode(), restResultEnum.getDescribe());
    }
}

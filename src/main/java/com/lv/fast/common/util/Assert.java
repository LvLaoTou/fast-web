package com.lv.fast.common.util;

import cn.hutool.core.collection.CollectionUtil;
import com.lv.fast.common.valid.Describe;
import com.lv.fast.common.constant.RestResultCodeConstant;
import com.lv.fast.exception.MyException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * 自定义断言
 * @author lvlaotou
 */
public class Assert {

    private Assert(){}

    /**
     * 断言不为空，为空则抛出异常
     * @param target 目标对象
     * @param message 错误描述信息
     */
    public static void assertNotNull(Object target,String message){
        assertNotNull(target, RestResultCodeConstant.PARAM_ERROR, message);
    }

    /**
     * 断言不为空，为空则抛出异常
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
     * 断言不为空，为空则抛出异常
     * @param target 目标对象
     * @param describe 错误信息
     */
    public static void assertNotNull(Object target, Describe describe){
        assertNotNull(target, describe.getCode().toString(), describe.getDescribe());
    }

    /**
     * 断言不为空，为空则抛出异常
     * @param target 目标对象
     * @param message 错误描述信息
     */
    public static void assertNotNull(String target,String message){
        assertNotNull(target, RestResultCodeConstant.PARAM_ERROR, message);
    }

    /**
     * 断言不为空，为空则抛出异常
     * @param target 目标对象
     * @param code 错误状态码
     * @param message 错误描述信息
     */
    public static void assertNotNull(String target, String code, String message){
        if (org.apache.commons.lang3.StringUtils.isBlank(target)){
            throw new MyException(code, message);
        }
    }

    /**
     * 断言不为空，为空则抛出异常
     * @param target 目标对象
     * @param describe 错误信息
     */
    public static void assertNotNull(String target, Describe describe){
        assertNotNull(target, describe.getCode().toString().toString(), describe.getDescribe());
    }

    /**
     * 断言不为空  为空则抛出自定义异常
     * @param target 目标集合
     * @param message 描述信息
     */
    public static void assertNotNull(Collection<?> target, String message){
        assertNotNull(target, RestResultCodeConstant.PARAM_ERROR, message);
    }

    /**
     * 断言为空  不为空则抛出自定义异常
     * @param target 目标集合
     * @param describe 描述信息
     */
    public static void assertNotNull(Collection<?> target, Describe describe){
        assertNotNull(target, describe.getCode().toString(), describe.getDescribe());
    }

    /**
     * 断言为空  不为空则抛出自定义异常
     * @param target 目标集合
     * @param code 错误码
     * @param message 描述信息
     */
    public static void assertNotNull(Collection<?> target, String code, String message){
        if (CollectionUtil.isEmpty(target)){
            throw new MyException(code, message);
        }
    }

    /**
     * 断言不为空，为空则抛出异常
     * @param target 目标对象
     * @param message 错误描述信息
     */
    public static void assertNotNull(Object[] target,String message){
        assertNotNull(target, RestResultCodeConstant.PARAM_ERROR, message);
    }

    /**
     * 断言不为空，为空则抛出异常
     * @param target 目标对象
     * @param describe 错误描述信息
     */
    public static void assertNotNull(Object[] target,Describe describe){
        assertNotNull(target, describe.getCode().toString(), describe.getDescribe());
    }

    /**
     * 断言不为空，为空则抛出异常
     * @param target 目标对象
     * @param code 错误码
     * @param message 错误描述信息
     */
    public static void assertNotNull(Object[] target, String code, String message){
        if (target == null || target.length == 0){
            throw new MyException(code, message);
        }
    }

    /**
     * 断言为true  如果为false 抛出自定义异常
     * @param flag 条件
     * @param message 错误描述
     */
    public static void assertIsTrue(boolean flag,String message){
        assertIsTrue(flag, RestResultCodeConstant.PARAM_ERROR, message);
    }

    /**
     * 断言为true  如果为false 抛出自定义异常
     * @param flag 条件
     * @param message 错误描述
     */
    public static void assertIsTrue(boolean flag, String code, String message){
        if (!flag){
            throw new MyException(code, message);
        }
    }
    /**
     * 断言为true  如果为false 抛出自定义异常
     * @param flag 条件
     * @param describe 错误信息
     */
    public static void assertIsTrue(boolean flag, Describe describe){
        assertIsTrue(flag, describe.getCode().toString(), describe.getDescribe());
    }

    /**
     * 断言为false  如果为true 抛出自定义异常
     * @param flag 条件
     * @param message 错误描述
     */
    public static void assertIsFalse(boolean flag,String message){
        assertIsFalse(flag, RestResultCodeConstant.PARAM_ERROR, message);
    }

    /**
     * 断言为false  如果为true 抛出自定义异常
     * @param flag 条件
     * @param message 错误描述
     */
    public static void assertIsFalse(boolean flag, String code, String message){
        if (flag){
            throw new MyException(code, message);
        }
    }
    /**
     * 断言为false  如果为true 抛出自定义异常
     * @param flag 条件
     * @param describe 错误信息
     */
    public static void assertIsFalse(boolean flag, Describe describe){
        assertIsFalse(flag, describe.getCode().toString(), describe.getDescribe());
    }

    /**
     * 断言为空，不为空则抛出异常
     * @param target 目标对象
     * @param code 错误状态码
     * @param message 错误描述信息
     */
    public static void assertIsNull(String target, String code, String message){
        if (StringUtils.isNoneBlank(target)){
            throw new MyException(code, message);
        }
    }

    /**
     * 断言为空，不为空则抛出异常
     * @param target 目标对象
     * @param describe 错误信息
     */
    public static void assertIsNull(String target, Describe describe){
        assertIsNull(target, describe.getCode().toString(), describe.getDescribe());
    }

    /**
     * 断言为空，不为空则抛出异常
     * @param target
     * @param message
     */
    public static void assertIsNull(String target,String message){
        assertIsNull(target, RestResultCodeConstant.PARAM_ERROR, message);
    }

    /**
     * 断言为空  不为空则抛出自定义异常
     * @param target 目标集合
     * @param message 描述信息
     */
    public static void assertIsNull(Collection<?> target, String message){
        assertIsNull(target, RestResultCodeConstant.PARAM_ERROR, message);
    }

    /**
     * 断言为空  不为空则抛出自定义异常
     * @param target 目标集合
     * @param describe 描述信息
     */
    public static void assertIsNull(Collection<?> target, Describe describe){
        assertIsNull(target, describe.getCode().toString(), describe.getDescribe());
    }

    /**
     * 断言为空  不为空则抛出自定义异常
     * @param target 目标集合
     * @param code 错误码
     * @param message 描述信息
     */
    public static void assertIsNull(Collection<?> target, String code, String message){
        if (CollectionUtil.isNotEmpty(target)){
            throw new MyException(code, message);
        }
    }

    /**
     * 断言为空，不为空则抛出异常
     * @param target 目标对象
     * @param message 错误描述信息
     */
    public static void assertIsNull(Object[] target,String message){
        assertIsNull(target, RestResultCodeConstant.PARAM_ERROR, message);
    }

    /**
     * 断言为空，不为空则抛出异常
     * @param target 目标对象
     * @param describe 错误描述信息
     */
    public static void assertIsNull(Object[] target,Describe describe){
        assertIsNull(target, describe.getCode().toString(), describe.getDescribe());
    }

    /**
     * 断言为空，不为空则抛出异常
     * @param target 目标对象
     * @param code 错误码
     * @param message 错误描述信息
     */
    public static void assertIsNull(Object[] target, String code, String message){
        if (target != null && target.length != 0){
            throw new MyException(code, message);
        }
    }

    /**
     * 断言为空，不为空则抛出异常
     * @param target 目标对象
     * @param message 错误描述信息
     */
    public static void assertIsNull(Object target,String message){
        assertIsNull(target, RestResultCodeConstant.PARAM_ERROR, message);
    }

    /**
     * 断言为空，不为空则抛出异常
     * @param target 目标对象
     * @param code 错误状态码
     * @param message 错误描述信息
     */
    public static void assertIsNull(Object target, String code, String message){
        if (target != null){
            throw new MyException(code, message);
        }
    }

    /**
     * 断言为空，不为空则抛出异常
     * @param target 目标对象
     * @param describe 错误信息
     */
    public static void assertIsNull(Object target, Describe describe){
        assertIsNull(target, describe.getCode().toString(), describe.getDescribe());
    }
}

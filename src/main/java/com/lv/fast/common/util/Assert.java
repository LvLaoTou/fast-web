package com.lv.fast.common.util;

import cn.hutool.core.collection.CollectionUtil;
import com.lv.fast.common.constant.JsonConstant;
import com.lv.fast.common.valid.Describe;
import com.lv.fast.common.constant.RestResultCodeConstant;
import com.lv.fast.exception.BusinessException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * 自定义断言
 * @author lvlaotou
 */
@Slf4j
public class Assert {

    private Assert(){}

    /******************************************************************Assert Object Not Null***********************************************************************/

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
     * @param message 错误描述信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertNotNull(Object target,String message, Object logOutData){
        assertNotNull(target, RestResultCodeConstant.PARAM_ERROR, message, logOutData);
    }

    /**
     * 断言不为空，为空则抛出异常
     * @param target 目标对象
     * @param code 错误状态码
     * @param message 错误描述信息
     */
    public static void assertNotNull(Object target, String code, String message){
        assertNotNull(target, code, message, null);
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
     * @param describe 错误信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertNotNull(Object target, Describe describe, Object logOutData){
        assertNotNull(target, describe.getCode().toString(), describe.getDescribe(), logOutData);
    }


    /**
     * 断言不为空，为空则抛出异常
     * @param target 目标对象
     * @param code 错误状态码
     * @param message 错误描述信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertNotNull(Object target, String code, String message, Object logOutData){
        if (target == null){
            logOutError(logOutData);
            throw new BusinessException(code, message);
        }
    }

    /******************************************************************Assert String Not Null***********************************************************************/

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
     * @param message 错误描述信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertNotNull(String target,String message, Object logOutData){
        assertNotNull(target, RestResultCodeConstant.PARAM_ERROR, message, logOutData);
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
     * 断言不为空，为空则抛出异常
     * @param target 目标对象
     * @param describe 错误信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertNotNull(String target, Describe describe, Object logOutData){
        assertNotNull(target, describe.getCode().toString().toString(), describe.getDescribe(), logOutData);
    }


    /**
     * 断言不为空，为空则抛出异常
     * @param target 目标对象
     * @param code 错误状态码
     * @param message 错误描述信息
     */
    public static void assertNotNull(String target, String code, String message){
        assertNotNull(target, code, message, null);
    }

    /**
     * 断言不为空，为空则抛出异常
     * @param target 目标对象
     * @param code 错误状态码
     * @param message 错误描述信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertNotNull(String target, String code, String message, Object logOutData){
        if (StringUtils.isBlank(target)){
            logOutError(logOutData);
            throw new BusinessException(code, message);
        }
    }

    /******************************************************************Assert Collection Not Null***********************************************************************/

    /**
     * 断言不为空  为空则抛出自定义异常
     * @param target 目标集合
     * @param message 描述信息
     */
    public static void assertNotNull(Collection<?> target, String message){
        assertNotNull(target, RestResultCodeConstant.PARAM_ERROR, message);
    }

    /**
     * 断言不为空  为空则抛出自定义异常
     * @param target 目标集合
     * @param message 描述信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertNotNull(Collection<?> target, String message, Object logOutData){
        assertNotNull(target, RestResultCodeConstant.PARAM_ERROR, message, logOutData);
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
     * @param describe 描述信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertNotNull(Collection<?> target, Describe describe, Object logOutData){
        assertNotNull(target, describe.getCode().toString(), describe.getDescribe(), logOutData);
    }

    /**
     * 断言为空  不为空则抛出自定义异常
     * @param target 目标集合
     * @param code 错误码
     * @param message 描述信息
     */
    public static void assertNotNull(Collection<?> target, String code, String message){
        assertNotNull(target, code, message, null);
    }

    /**
     * 断言为空  不为空则抛出自定义异常
     * @param target 目标集合
     * @param code 错误码
     * @param message 描述信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertNotNull(Collection<?> target, String code, String message, Object logOutData){
        if (CollectionUtil.isEmpty(target)){
            logOutError(logOutData);
            throw new BusinessException(code, message);
        }
    }

    /******************************************************************Assert Array Not Null***********************************************************************/

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
     * @param message 错误描述信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertNotNull(Object[] target,String message, Object logOutData){
        assertNotNull(target, RestResultCodeConstant.PARAM_ERROR, message, logOutData);
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
     * @param describe 错误描述信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertNotNull(Object[] target,Describe describe, Object logOutData){
        assertNotNull(target, describe.getCode().toString(), describe.getDescribe(), logOutData);
    }

    /**
     * 断言不为空，为空则抛出异常
     * @param target 目标对象
     * @param code 错误码
     * @param message 错误描述信息
     */
    public static void assertNotNull(Object[] target, String code, String message){
        assertNotNull(target, code, message, null);
    }

    /**
     * 断言不为空，为空则抛出异常
     * @param target 目标对象
     * @param code 错误码
     * @param message 错误描述信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertNotNull(Object[] target, String code, String message, Object logOutData){
        if (target == null || target.length == 0){
            logOutError(logOutData);
            throw new BusinessException(code, message);
        }
    }

    /******************************************************************Assert Boolean Is True***********************************************************************/

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
     * @param logOutData 需要日志打印的数据
     */
    public static void assertIsTrue(boolean flag, String message, Object logOutData){
        assertIsTrue(flag, RestResultCodeConstant.PARAM_ERROR, message, logOutData);
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
     * 断言为true  如果为false 抛出自定义异常
     * @param flag 条件
     * @param describe 错误信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertIsTrue(boolean flag, Describe describe, Object logOutData){
        assertIsTrue(flag, describe.getCode().toString(), describe.getDescribe(), logOutData);
    }

    /**
     * 断言为true  如果为false 抛出自定义异常
     * @param flag 条件
     * @param message 错误描述
     */
    public static void assertIsTrue(boolean flag, String code, String message){
        assertIsTrue(flag, code, message, null);
    }

    /**
     * 断言为true  如果为false 抛出自定义异常
     * @param flag 条件
     * @param message 错误描述
     * @param logOutData 需要日志打印的数据
     */
    public static void assertIsTrue(boolean flag, String code, String message, Object logOutData){
        if (!flag){
            logOutError(logOutData);
            throw new BusinessException(code, message);
        }
    }

    /******************************************************************Assert Boolean Is False***********************************************************************/

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
     * @param logOutData 需要日志打印的数据
     */
    public static void assertIsFalse(boolean flag,String message, Object logOutData){
        assertIsFalse(flag, RestResultCodeConstant.PARAM_ERROR, message, logOutData);
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
     * 断言为false  如果为true 抛出自定义异常
     * @param flag 条件
     * @param describe 错误信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertIsFalse(boolean flag, Describe describe, Object logOutData){
        assertIsFalse(flag, describe.getCode().toString(), describe.getDescribe(), logOutData);
    }

    /**
     * 断言为false  如果为true 抛出自定义异常
     * @param flag 条件
     * @param message 错误描述
     */
    public static void assertIsFalse(boolean flag, String code, String message){
        assertIsFalse(flag, code, message, null);
    }

    /**
     * 断言为false  如果为true 抛出自定义异常
     * @param flag 条件
     * @param message 错误描述
     * @param logOutData 需要日志打印的数据
     */
    public static void assertIsFalse(boolean flag, String code, String message, Object logOutData){
        if (flag){
            logOutError(logOutData);
            throw new BusinessException(code, message);
        }
    }

    /******************************************************************Assert String Is Null***********************************************************************/


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
     * @param target 目标对象
     * @param describe 错误信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertIsNull(String target, Describe describe, Object logOutData){
        assertIsNull(target, describe.getCode().toString(), describe.getDescribe(), logOutData);
    }

    /**
     * 断言为空，不为空则抛出异常
     * @param target 目标对象
     * @param message 错误描述
     */
    public static void assertIsNull(String target,String message){
        assertIsNull(target, RestResultCodeConstant.PARAM_ERROR, message);
    }

    /**
     * 断言为空，不为空则抛出异常
     * @param target 目标对象
     * @param message 错误描述
     * @param logOutData 需要日志打印的数据
     */
    public static void assertIsNull(String target,String message, Object logOutData){
        assertIsNull(target, RestResultCodeConstant.PARAM_ERROR, message, logOutData);
    }

    /**
     * 断言为空，不为空则抛出异常
     * @param target 目标对象
     * @param code 错误状态码
     * @param message 错误描述信息
     */
    public static void assertIsNull(String target, String code, String message){
        assertIsNull(target, code, message, null);
    }

    /**
     * 断言为空，不为空则抛出异常
     * @param target 目标对象
     * @param code 错误状态码
     * @param message 错误描述信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertIsNull(String target, String code, String message, Object logOutData){
        if (StringUtils.isNoneBlank(target)){
            logOutError(logOutData);
            throw new BusinessException(code, message);
        }
    }

    /******************************************************************Assert Collection Is Null***********************************************************************/

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
     * @param message 描述信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertIsNull(Collection<?> target, String message, Object logOutData){
        assertIsNull(target, RestResultCodeConstant.PARAM_ERROR, message, logOutData);
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
     * @param describe 描述信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertIsNull(Collection<?> target, Describe describe, Object logOutData){
        assertIsNull(target, describe.getCode().toString(), describe.getDescribe(), logOutData);
    }

    /**
     * 断言为空  不为空则抛出自定义异常
     * @param target 目标集合
     * @param code 错误码
     * @param message 描述信息
     */
    public static void assertIsNull(Collection<?> target, String code, String message){
        assertIsNull(target, code, message, null);
    }

    /**
     * 断言为空  不为空则抛出自定义异常
     * @param target 目标集合
     * @param code 错误码
     * @param message 描述信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertIsNull(Collection<?> target, String code, String message, Object logOutData){
        if (CollectionUtil.isNotEmpty(target)){
            logOutError(logOutData);
            throw new BusinessException(code, message);
        }
    }

    /******************************************************************Assert Array Is Null***********************************************************************/

    /**
     * 断言为空，不为空则抛出异常
     * @param target 目标对象
     * @param message 错误描述信息
     */
    public static void assertIsNull(Object[] target, String message){
        assertIsNull(target, RestResultCodeConstant.PARAM_ERROR, message);
    }

    /**
     * 断言为空，不为空则抛出异常
     * @param target 目标对象
     * @param message 错误描述信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertIsNull(Object[] target, String message, Object logOutData){
        assertIsNull(target, RestResultCodeConstant.PARAM_ERROR, message, logOutData);
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
     * @param describe 错误描述信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertIsNull(Object[] target,Describe describe, Object logOutData){
        assertIsNull(target, describe.getCode().toString(), describe.getDescribe(), logOutData);
    }

    /**
     * 断言为空，不为空则抛出异常
     * @param target 目标对象
     * @param code 错误码
     * @param message 错误描述信息
     */
    public static void assertIsNull(Object[] target, String code, String message){
        assertIsNull(target, code, message, null);
    }

    /**
     * 断言为空，不为空则抛出异常
     * @param target 目标对象
     * @param code 错误码
     * @param message 错误描述信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertIsNull(Object[] target, String code, String message, Object logOutData){
        if (target != null && target.length != 0){
            logOutError(logOutData);
            throw new BusinessException(code, message);
        }
    }

    /******************************************************************Assert Object Is Null***********************************************************************/

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
     * @param message 错误描述信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertIsNull(Object target,String message, Object logOutData){
        assertIsNull(target, RestResultCodeConstant.PARAM_ERROR, message, logOutData);
    }

    /**
     * 断言为空，不为空则抛出异常
     * @param target 目标对象
     * @param describe 错误信息
     */
    public static void assertIsNull(Object target, Describe describe){
        assertIsNull(target, describe.getCode().toString(), describe.getDescribe());
    }

    /**
     * 断言为空，不为空则抛出异常
     * @param target 目标对象
     * @param describe 错误信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertIsNull(Object target, Describe describe, Object logOutData){
        assertIsNull(target, describe.getCode().toString(), describe.getDescribe(), logOutData);
    }

    /**
     * 断言为空，不为空则抛出异常
     * @param target 目标对象
     * @param code 错误状态码
     * @param message 错误描述信息
     */
    public static void assertIsNull(Object target, String code, String message){
        assertIsNull(target, code, message, null);
    }

    /**
     * 断言为空，不为空则抛出异常
     * @param target 目标对象
     * @param code 错误状态码
     * @param message 错误描述信息
     * @param logOutData 需要日志打印的数据
     */
    public static void assertIsNull(Object target, String code, String message, Object logOutData){
        if (target != null){
            logOutError(logOutData);
            throw new BusinessException(code, message);
        }
    }

    /******************************************************************Log Out***********************************************************************/

    /**
     * 输出错误日志
     * @param logOutData 日志输出数据
     */
    @SneakyThrows
    public static void logOutError(Object logOutData){
        if (logOutData != null){
            log.error("断言不为空日志数据：", JsonConstant.MAPPER.writeValueAsString(logOutData));
        }
    }
}

package com.lv.fast.common.util;

import cn.hutool.core.util.StrUtil;
import com.lv.fast.common.entity.Code;
import com.lv.fast.exception.BusinessException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 枚举工具类
 * @author lvlaotou
 */
public class EnumUtil {

    /**
     * 通过code获取枚举对象
     * @param target 枚举对象
     * @param code code
     * @param errorDescribe 错误描述
     * @param <T> 泛型
     * @return 泛型对象
     */
    public static  <P,T extends Enum<? extends Code<P>>> T getEnumByCode(Class<T> target, Object code, String errorDescribe){
        return getEnumByCode(target, code, true, errorDescribe);
    }

    /**
     * 通过code获取枚举对象
     * @param target 枚举对象
     * @param code code
     * @param errorDescribe 错误描述
     * @param <T> 泛型
     * @param ignoreCase 忽略大小写
     * @return 泛型对象
     */
    public static  <P,T extends Enum<? extends Code<P>>> T getEnumByCode(Class<T> target, Object code, boolean ignoreCase, String errorDescribe){
        Assert.notEmpty(code, StrUtil.isNotBlank(errorDescribe) ? errorDescribe : "code不能为空");
        T[] enumConstants = target.getEnumConstants();
        List<T> baseList = Arrays.stream(enumConstants).filter(t -> {
            if (ignoreCase){
                return code.toString().equalsIgnoreCase(((Code)t).getCode().toString());
            }
            return code.toString().equals(((Code)t).getCode().toString());
        }).collect(Collectors.toList());
        Assert.notEmpty(baseList,errorDescribe);
        Assert.isTrue(baseList.size() == 1, errorDescribe+"，预计1个值匹配，实际"+baseList.size()+"个匹配");
        return baseList.get(0);
    }

    /**
     * 校验指定值是否在目标枚举范围内
     * @param target 目标对象
     * @param code 需要校验的值
     * @param <T> 泛型
     * @return 泛型对象
     */
    public static  <E,T extends Enum<? extends Code<E>>> boolean isValid(Class<T> target, E code, boolean ignoreCase){
        Code[] enumConstants = (Code[]) target.getEnumConstants();
        if (enumConstants == null || enumConstants.length < 1){
            return false;
        }
        long count = Arrays.stream(enumConstants).filter(enumValid -> {
            if (code == null){
                return false;
            }
            if (code instanceof String){
                return ignoreCase ? ((String) code).equalsIgnoreCase((String) enumValid.getCode())
                        : (code).equals(enumValid.getCode());
            }
            if (code instanceof Integer || code instanceof Long){
                return (code).equals(enumValid.getCode());
            }
            throw new BusinessException("枚举泛型非法");
        }).count();
        return count == 1;
    }

    /**
     * 校验指定值是否在目标枚举范围内
     * @param target 目标对象
     * @param code 需要校验的值
     * @param <T> 泛型
     * @return 泛型对象
     */
    public static  <E,T extends Enum<? extends Code<E>>> boolean isValid(Class<T> target, E code){
        return isValid(target, code, true);
    }

    /**
     * 校验指定值是否在目标枚举范围内
     * @param target 目标对象
     * @param code 需要校验的值
     * @param exclude 需要排除的值
     * @param <T> 泛型
     * @return 泛型对象
     */
    public static  <T extends Enum<? extends Code>> boolean isValid(Class<T> target, String code, String[] exclude){
        Code[] enumConstants = (Code[]) target.getEnumConstants();
        long count = Arrays.stream(enumConstants).filter(enumValid -> Objects.equals(enumValid.getCode(), code)).count();
        return count == 1;
    }
}

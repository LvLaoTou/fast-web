package com.lv.fast.common.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.lv.fast.common.entity.EnumInterface;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 枚举工具类
 * @author lvlaotou
 */
public class EnumUtil {

    public static  <T extends Enum<? extends EnumInterface<?>>> T getEnumByCode(Class<T> target, Object code){
        return getEnumByCode(target, code, null);
    }

    /**
     * 通过code获取枚举对象
     * @param target 枚举对象
     * @param code code
     * @param errorDescribe 错误描述
     * @param <T> 泛型
     * @return 泛型对象
     */
    public static  <T extends Enum<? extends EnumInterface<?>>> T getEnumByCode(Class<T> target, Object code, String errorDescribe){
        T[] enumConstants = target.getEnumConstants();
        Assert.notEmpty(enumConstants, "枚举对象为空");
        if (StrUtil.isBlank(errorDescribe)){
            EnumInterface enumConstant = (EnumInterface) enumConstants[0];
            errorDescribe = enumConstant.errorDescribe();
        }
        Assert.notEmpty(code, StrUtil.isNotBlank(errorDescribe) ? errorDescribe : "code不能为空");
        List<T> baseList = Arrays.stream(enumConstants).filter(t -> ObjectUtil.equals(code.toString(), ((EnumInterface<?>)t).getCode().toString())).collect(Collectors.toList());
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
    public static  <T extends Enum<? extends EnumInterface<?>>> boolean isValid(Class<T> target, Object code){
        if (Objects.isNull(code)){
            return false;
        }
        T[] enumConstants = target.getEnumConstants();
        if (ArrayUtil.isEmpty(enumConstants)){
            return false;
        }
        long count = Arrays.stream(enumConstants).filter(enumValid ->
            ObjectUtil.equals(code, ((EnumInterface<?>)enumValid).getCode())
        ).count();
        return count == 1;
    }

    /**
     * 校验指定值是否在目标枚举范围内
     * @param target 目标对象
     * @param code 需要校验的值
     * @param exclude 需要排除的值
     * @param <T> 泛型
     * @return 泛型对象
     */
    @SuppressWarnings("unused")
    public static  <T extends Enum<? extends EnumInterface<?>>> boolean isValid(Class<T> target, Object code, Set<Object> exclude){
        T[] enumConstants = target.getEnumConstants();
        Optional<T> optional = Arrays.stream(enumConstants)
                .filter(enumValid -> (CollectionUtil.isEmpty(exclude) || !exclude.contains(code))
                        && ObjectUtil.equals(code, ((EnumInterface<?>) enumValid).getCode())).findAny();
        return optional.isPresent();
    }
}

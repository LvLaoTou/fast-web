package com.lv.fast.util;

import com.lv.fast.common.Code;
import com.lv.fast.common.Describe;
import com.lv.fast.constant.RestResultCodeConstant;
import com.lv.fast.exception.MyException;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 枚举工具类
 * @author lv
 * @version 1.0.0
 */
public class EnumUtil {

    private EnumUtil(){}

    /**
     * 通过code获取枚举对象
     * @param target 枚举对象
     * @param code code
     * @param <T> 泛型
     * @return 泛型对象
     */
    public static  <T extends Enum<? extends Code>> T getEnumByCode(Class<T> target, String code){
        T[] enumConstants = target.getEnumConstants();
        List<T> baseList = Arrays.stream(enumConstants).filter(t -> Objects.equals(((Code)t).getCode(), code))
                .collect(Collectors.toList());
        Assert.assertNotNull(baseList,"没有匹配的枚举");
        if(baseList.size() > 1){
            throw new MyException(RestResultCodeConstant.PARAM_ERROR, "枚举异常，预计1个枚举匹配，实际"+baseList.size()+"个匹配");
        }
        return baseList.get(0);
    }

    /**
     * 获取枚举描述信息
     * @param target 枚举对象
     * @param code 标识码
     * @param <T> 枚举泛型
     * @return 描述
     */
    public static  <T extends Enum<? extends Describe>> String getEnumDescribeByCode(Class<T> target, String code){
        T[] enumConstants = target.getEnumConstants();
        List<T> baseList = Arrays.stream(enumConstants).filter(t -> Objects.equals(((Describe)t).getCode(), code))
                .collect(Collectors.toList());
        Assert.assertNotNull(baseList,"没有匹配的枚举");
        if(baseList.size() > 1){
            throw new MyException(RestResultCodeConstant.PARAM_ERROR, "枚举异常，预计1个枚举匹配，实际"+baseList.size()+"个匹配");
        }
        T t = baseList.get(0);
        String describe = ((Describe) t).getDescribe();
        return describe;
    }

    /**
     * 校验指定值是否在目标枚举范围内
     * @param target 目标对象
     * @param code 需要校验的值
     * @param <T> 泛型
     * @return 泛型对象
     */
    public static  <T extends Enum<? extends Code>> boolean isValid(Class<T> target, String code){
        return isValid(target, code, true);
    }

    /**
     * 校验指定值是否在目标枚举范围内
     * @param target 目标对象
     * @param code 需要校验的值
     * @param <T> 泛型
     * @return 泛型对象
     */
    public static  <T extends Enum<? extends Code>> boolean isValid(Class<T> target, String code, boolean ignoreCase){
        Code[] enumConstants = (Code[]) target.getEnumConstants();
        if (enumConstants == null || enumConstants.length < 1){
            return false;
        }
        long count = Arrays.stream(enumConstants).filter(enumValid -> {
            if (StringUtils.isBlank(code)){
                return false;
            }
            return ignoreCase ? code.equalsIgnoreCase(enumValid.getCode()) : code.equals(enumValid.getCode());
        }).count();
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
    public static  <T extends Enum<? extends Code>> boolean isValid(Class<T> target, String code, String[] exclude){
        Code[] enumConstants = (Code[]) target.getEnumConstants();
        long count = Arrays.stream(enumConstants).filter(enumValid -> Objects.equals(enumValid.getCode(), code)).count();
        return count == 1;
    }
}

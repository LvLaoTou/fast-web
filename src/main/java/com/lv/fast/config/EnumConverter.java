package com.lv.fast.config;

import com.lv.fast.common.entity.EnumInterface;
import com.lv.fast.common.util.EnumUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.lang.reflect.Method;

/**
 * @author lvlaotou
 */
@Slf4j
public class EnumConverter<P, E extends Enum<? extends EnumInterface<P>>> implements Converter<P, E> {

    private final Class<E> enumClass;

    public EnumConverter(Class<E> enumClass){
        this.enumClass = enumClass;
    }

    @SneakyThrows
    @Override
    public E convert(P code) {
        String errorDescribe = "无效枚举参数";
        try{
            Method method = enumClass.getMethod("errorDescribe");
            Object invoke = method.invoke(null);
            errorDescribe = invoke.toString();
        }catch (Exception ignored){}
        return EnumUtil.getEnumByCode(enumClass, code, errorDescribe);
    }
}

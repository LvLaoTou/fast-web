package com.lv.fast.config;

import com.lv.fast.common.entity.Code;
import com.lv.fast.common.util.EnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

/**
 * @author lvlaotou
 */
@Slf4j
public class EnumConverter<P,T extends Enum<? extends Code<P>>> implements Converter<P, T> {

    private Class<T> enumClass;

    public EnumConverter(Class<T> enumClass){
        this.enumClass = enumClass;
    }

    @Override
    public T convert(P source) {
        return EnumUtil.getEnumByCode(enumClass, source, "没有匹配的枚举");
    }
}

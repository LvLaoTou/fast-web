package com.lv.fast.common.valid;

import cn.hutool.core.collection.CollectionUtil;
import com.lv.fast.common.entity.EnumInterface;
import com.lv.fast.common.util.Assert;
import com.lv.fast.common.util.EnumUtil;
import com.lv.fast.exception.BusinessException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author lvlaotou
 */
@Slf4j
public class EnumCheckValidator implements ConstraintValidator<EnumCheck,Object> {
    private Class<? extends Enum<? extends EnumInterface<?>>> enumClass;

    private boolean isAllMatch;

    @Override
    public void initialize(EnumCheck enumCheck) {
        enumClass = enumCheck.enumClass();
        isAllMatch = enumCheck.isAllMatch();
    }

    @Override
    public boolean isValid(Object code, ConstraintValidatorContext context) {
        if (code == null){
            return true;
        }
        Assert.notEmpty(enumClass,"枚举参数校验异常");
        Collection<?> target = null;
        try{
            if (code instanceof Collection<?>){
                target = (Collection<?>) code;
            }
            if (code.getClass().isArray()){
                assert code instanceof Object[];
                target = Arrays.stream((Object[]) code).collect(Collectors.toSet());
            }
        }catch (ClassCastException e){
            log.error("EnumCheck注解目标对象转换异常", e);
            throw new BusinessException("EnumCheck注解类型转换异常");
        }
        boolean flag;
        if (CollectionUtil.isNotEmpty(target)){
            // 校验内容
            if (isAllMatch){
                flag = target.stream().allMatch(targetCode-> EnumUtil.isValid(enumClass, targetCode));
            }else {
                flag = target.stream().anyMatch(targetCode->EnumUtil.isValid(enumClass, targetCode));
            }
        }else {
            flag = EnumUtil.isValid(enumClass, code);
        }
        return flag;
    }
}

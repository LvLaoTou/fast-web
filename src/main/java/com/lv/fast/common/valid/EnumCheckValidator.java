package com.lv.fast.common.valid;

import cn.hutool.core.collection.CollectionUtil;
import com.lv.fast.common.entity.EnumInterface;
import com.lv.fast.common.util.Assert;
import com.lv.fast.common.util.EnumUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author lvlaotou
 */
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
        Assert.notNull(enumClass,"枚举参数校验异常");
        Collection<?> target = null;
        if (code instanceof Collection<?> collection){
            target = collection;
        }else if (code instanceof Object[] array){
            target = Arrays.stream(array).collect(Collectors.toSet());
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

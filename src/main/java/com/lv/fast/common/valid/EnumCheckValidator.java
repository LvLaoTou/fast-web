package com.lv.fast.common.valid;

import com.lv.fast.common.entity.Code;
import com.lv.fast.common.util.Assert;
import com.lv.fast.common.util.EnumUtil;
import com.lv.fast.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author lvlaotou
 */
@Slf4j
public class EnumCheckValidator implements ConstraintValidator<EnumCheck,Object> {
    private Class<? extends Enum<? extends Code<Object>>> enumClass;

    private String[] exclude;

    private boolean isAllMatch;

    private boolean excludeIgnoreCase;

    private boolean matchIgnoreCase;

    @Override
    public void initialize(EnumCheck enumCheck) {
        enumClass = enumCheck.enumClass();
        exclude = enumCheck.exclude();
        isAllMatch = enumCheck.isAllMatch();
        excludeIgnoreCase = enumCheck.excludeIgnoreCase();
        matchIgnoreCase = enumCheck.matchIgnoreCase();
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
                target = ((Collection<?>) code);
            }
            if (code.getClass().isArray()){
                if (code instanceof Object[]) {
                    target = Arrays.stream((Object[]) code).collect(Collectors.toSet());
                }
            }
        }catch (ClassCastException e){
            log.error("EnumCheck注解目标对象转换异常", e);
            throw new BusinessException("EnumCheck注解类型转换异常");
        }
        boolean flag;
        if (target != null){
            // 校验需要排除的
            if (exclude != null){
                flag = target.stream().noneMatch(
                        targetCode-> Arrays.stream(exclude)
                                .anyMatch(excludeCode -> (excludeIgnoreCase ? targetCode.toString().equalsIgnoreCase(excludeCode) : targetCode.toString().equals(excludeCode))
                                )
                );
                if (!flag){
                    return false;
                }
            }
            // 校验内容
            if (isAllMatch){
                flag = target.stream().allMatch(targetCode-> EnumUtil.isValid(enumClass, targetCode, matchIgnoreCase));
            }else {
                flag = target.stream().anyMatch(targetCode->EnumUtil.isValid(enumClass, targetCode, matchIgnoreCase));
            }
        }else {
            flag = EnumUtil.isValid(enumClass, code);
        }
        return flag;
    }
}

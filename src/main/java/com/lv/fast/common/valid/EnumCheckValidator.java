package com.lv.fast.common.valid;

import com.lv.fast.common.util.Assert;
import com.lv.fast.exception.MyException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author lv
 * @version 1.0.0
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
        enumClass = (Class<? extends Enum<? extends Code<Object>>>) enumCheck.enumClass();
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
        Assert.assertNotNull(enumClass,"枚举参数校验异常");
        Collection<Object> target = null;
        try{
            if (code instanceof Collection){
                target = ((Collection<Object>) code);
            }
            if (code.getClass().isArray()){
                target = Arrays.stream((Object[]) code).collect(Collectors.toSet());
            }
        }catch (ClassCastException e){
            log.error("EnumCheck注解目标对象转换异常", e);
            throw new MyException("EnumCheck注解类型转换异常");
        }
        boolean flag;
        if (target != null){
            // 校验需要排除的
            if (exclude != null){
                flag = target.stream().allMatch(
                        targetCode->!Arrays.stream(exclude)
                                .anyMatch(excludeCode -> (excludeIgnoreCase ? targetCode.toString().equalsIgnoreCase(excludeCode) : targetCode.toString().equals(excludeCode))
                                )
                );
                if (!flag){
                    return false;
                }
            }
            // 校验内容
            if (isAllMatch){
                flag = target.stream().allMatch(targetCode->EnumUtil.isValid(enumClass, targetCode, matchIgnoreCase));
            }else {
                flag = target.stream().anyMatch(targetCode->EnumUtil.isValid(enumClass, targetCode, matchIgnoreCase));
            }
        }else {
            flag = EnumUtil.isValid(enumClass, code);
        }
        return flag;
    }
}

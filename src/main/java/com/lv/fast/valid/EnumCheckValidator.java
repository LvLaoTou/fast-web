package com.lv.fast.valid;

import com.lv.fast.common.Code;
import com.lv.fast.annotation.EnumCheck;
import com.lv.fast.util.Assert;
import com.lv.fast.util.EnumUtil;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author lv
 * @version 1.0.0
 */
@Slf4j
public class EnumCheckValidator implements ConstraintValidator<EnumCheck,String> {
    private Class<? extends Enum<? extends Code>> enumClass;

    private String[] exclude;

    @Override
    public void initialize(EnumCheck enumCheck) {
        enumClass = enumCheck.enumClass();
        exclude = enumCheck.exclude();
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        if (code == null){
            return true;
        }
        Assert.assertNotNull(enumClass,"枚举参数校验异常");
        try {
            boolean flag = true;
            if (exclude != null && exclude.length > 0){
                flag = !Arrays.stream(exclude).anyMatch(excludeCode-> Objects.equals(code, excludeCode));
            }
            return flag && EnumUtil.isValid(enumClass,code);
        } catch (Exception e) {
            log.error("校验枚举参数异常：",e);
        }
        return false;
    }

}

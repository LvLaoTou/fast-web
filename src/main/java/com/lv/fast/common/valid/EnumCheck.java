package com.lv.fast.common.valid;

import com.lv.fast.common.entity.EnumInterface;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用枚举作为限定条件的校验注解
 * @author lvlaotou
 */
@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumCheckValidator.class)
public @interface EnumCheck {

    String message() default "参数超出限制范围";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /** 指定枚举类 */
    Class<? extends Enum<? extends EnumInterface<?>>> enumClass();

    /** 需要排除的 */
    @SuppressWarnings("unused")
    String[] exclude() default { };

    /** 当作用于集合或者数组的时候 是否需要全部匹配  */
    boolean isAllMatch() default true;
}

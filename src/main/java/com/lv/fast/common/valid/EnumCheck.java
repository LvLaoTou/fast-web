package com.lv.fast.common.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
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
    Class<? extends Enum<? extends Code>> enumClass();

    /** 需要排除的 */
    String[] exclude() default { };

    /** 当作用于集合或者数组的时候 是否需要全部匹配  */
    boolean isAllMatch() default true;

    /** 需要排除的是否忽略大小写 */
    boolean excludeIgnoreCase() default true;

    /** 需要匹配的是否忽略大小写 */
    boolean matchIgnoreCase() default true;
}

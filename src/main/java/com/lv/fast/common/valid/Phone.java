package com.lv.fast.common.valid;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 手机号校验注解
 * @author lvlaotou
 */
@Documented
@ConstraintComposition(CompositionType.AND)
@Pattern(regexp = "1\\d{10}")
@Target({ ElementType.FIELD,ElementType.PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface Phone {
    String message() default "手机号码格式不合法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

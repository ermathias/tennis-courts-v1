package com.tenniscourts.exceptions;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AdminValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminConstraint {
    String message() default "Operation allowed only for administrators";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

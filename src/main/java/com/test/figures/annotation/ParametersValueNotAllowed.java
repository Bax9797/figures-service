package com.test.figures.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ParametersValueNotAllowedValidator.class)
@Documented
public @interface ParametersValueNotAllowed {
    String message() default "ERROR_FIGURE_PARAMETERS_NOT_ALLOWED";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

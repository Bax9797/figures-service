package com.test.figures.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class ParametersValueNotAllowedValidator implements ConstraintValidator<ParametersValueNotAllowed, Map<String, Double>> {

    @Override
    public void initialize(ParametersValueNotAllowed constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Map<String, Double> parameters, ConstraintValidatorContext constraintValidatorContext) {
        return parameters.values().stream().allMatch(p -> p > 0.0);
    }

}

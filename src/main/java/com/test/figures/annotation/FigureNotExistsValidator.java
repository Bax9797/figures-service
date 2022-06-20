package com.test.figures.annotation;

import com.test.figures.creator.FacadeFigureCreator;
import com.test.figures.model.CommandFigure;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class FigureNotExistsValidator implements ConstraintValidator<FigureNotExists, CommandFigure> {
    private final FacadeFigureCreator facadeFigureCreator;

    @Override
    public void initialize(FigureNotExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CommandFigure commandFigure, ConstraintValidatorContext constraintValidatorContext) {
        return facadeFigureCreator.existsFigureParameters(commandFigure);
    }
}

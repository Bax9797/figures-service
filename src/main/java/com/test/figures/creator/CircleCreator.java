package com.test.figures.creator;

import com.test.figures.entity.figures.Circle;
import com.test.figures.entity.figures.Figure;
import com.test.figures.model.CommandFigure;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CircleCreator implements FigureCreator {
    @Override
    public Class<?> getClassFigures() {
        return Circle.class;
    }

    @Override
    public String getTypesFigures() {
        return "CIRCLE";
    }

    @Override
    public Set<String> getParameter() {
        return Set.of("radius");
    }

    @Override
    public Figure putNewValues(CommandFigure commandFigure, Figure figure) {
        ((Circle) figure).setRadius(commandFigure.getParameters().get("radius"));
        return figure;
    }
}

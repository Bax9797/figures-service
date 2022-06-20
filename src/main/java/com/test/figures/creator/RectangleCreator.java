package com.test.figures.creator;

import com.test.figures.entity.figures.Figure;
import com.test.figures.entity.figures.Rectangle;
import com.test.figures.model.CommandFigure;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RectangleCreator implements FigureCreator {
    @Override
    public Class<?> getClassFigures() {
        return Rectangle.class;
    }

    @Override
    public String getTypesFigures() {
        return "RECTANGLE";
    }

    @Override
    public Set<String> getParameter() {
        return Set.of("width", "height");
    }

    @Override
    public Figure putNewValues(CommandFigure commandFigure, Figure figure) {
        ((Rectangle) figure).setWidth(commandFigure.getParameters().get("width"));
        ((Rectangle) figure).setHeight(commandFigure.getParameters().get("height"));
        return figure;
    }
}

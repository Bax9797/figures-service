package com.test.figures.creator;

import com.test.figures.entity.figures.Figure;
import com.test.figures.entity.figures.Square;
import com.test.figures.model.CommandFigure;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SquareCreator implements FigureCreator {
    @Override
    public Class<?> getClassFigures() {
        return Square.class;
    }

    @Override
    public String getTypesFigures() {
        return "SQUARE";
    }

    @Override
    public Set<String> getParameter() {
        return Set.of("width");
    }

    @Override
    public Figure putNewValues(CommandFigure commandFigure, Figure figure) {
        ((Square) figure).setWidth(commandFigure.getParameters().get("width"));
        return figure;
    }
}

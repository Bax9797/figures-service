package com.test.figures.creator;

import com.test.figures.entity.figures.Figure;
import com.test.figures.model.CommandFigure;

import java.util.Set;

public interface FigureCreator {
    Class<?> getClassFigures();

    String getTypesFigures();

    Set<String> getParameter();

    Figure putNewValues(CommandFigure commandFigure, Figure figure);
}

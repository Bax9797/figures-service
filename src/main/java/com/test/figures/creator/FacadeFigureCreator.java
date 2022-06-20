package com.test.figures.creator;

import com.test.figures.entity.figures.Figure;
import com.test.figures.model.CommandFigure;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FacadeFigureCreator {

    @Value("${app.big-figure.area}")
    private double areaAllowed;
    private Map<String, FigureCreator> typesMap;
    private Map<String, Set<String>> parameterTypesMap;

    public FacadeFigureCreator(Set<FigureCreator> figureCreators) {
        typesMap = figureCreators.stream().collect(Collectors.toMap(FigureCreator::getTypesFigures, Function.identity()));
        parameterTypesMap = new HashMap<>();
        for (Map.Entry<String, FigureCreator> stringFigureCreatorEntry : typesMap.entrySet()) {
            parameterTypesMap.put(stringFigureCreatorEntry.getKey(), stringFigureCreatorEntry.getValue().getParameter());
        }
    }

    public boolean existsFigure(String type) {
        return typesMap.containsKey(type.toUpperCase());
    }

    public Figure getFigure(CommandFigure commandFigure) {
        BeanWrapper wrapper = new BeanWrapperImpl(typesMap.get(commandFigure.getName().toUpperCase()).getClassFigures());
        wrapper.setPropertyValues(commandFigure.getParameters());
        return (Figure) wrapper.getWrappedInstance();
    }

    public boolean checkingTheAllowedAreaSize(Figure figure) {
        return areaAllowed > figure.getArea();
    }

    public boolean existsFigureParameters(CommandFigure commandFigure) {
        return existsFigure(commandFigure.getName()) && parameterTypesMap
                .get(commandFigure.getName()).equals(commandFigure.getParameters().keySet());
    }

    public Figure putNewValuesFacade(CommandFigure commandFigure, Figure figure) {
        return typesMap.get(commandFigure.getName().toUpperCase()).putNewValues(commandFigure, figure);
    }
}



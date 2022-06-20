package com.test.figures.model;


import com.test.figures.annotation.FigureNotExists;
import com.test.figures.annotation.ParametersValueNotAllowed;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@Accessors(chain = true)
@FigureNotExists
public class CommandFigure {
    @NotEmpty(message = "NAME_NOT_EMPTY")
    @NotNull(message = "NAME_NOT_NULL")
    private String name;
    @NotEmpty(message = "PARAMETERS_NOT_EMPTY")
    @NotNull(message = "PARAMETERS_NOT_NULL")
    @ParametersValueNotAllowed
    private Map<String, Double> parameters;
}

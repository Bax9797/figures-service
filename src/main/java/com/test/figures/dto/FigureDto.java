package com.test.figures.dto;

import com.test.figures.entity.figures.Figure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class FigureDto {
    private int id;
    private String name;
    private Map<String, Double> parameters;

    public FigureDto(Figure figure) {
        this.id = figure.getId();
        this.name = figure.getType();
        this.parameters = figure.getParameters();
    }
}

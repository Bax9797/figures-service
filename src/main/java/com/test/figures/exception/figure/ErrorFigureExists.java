package com.test.figures.exception.figure;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ErrorFigureExists {
    private String errorCode;
    private int id;
}

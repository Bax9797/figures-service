package com.test.figures.exception.figure;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FigureNotFoundException extends RuntimeException {
    private int id;

    public FigureNotFoundException(int id) {
        super();
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

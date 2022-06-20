package com.test.figures.exception.figure.advice;

import com.test.figures.exception.figure.ErrorFigureExists;
import com.test.figures.exception.figure.FigureNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FigureNotFoundExceptionHandler {

    @ExceptionHandler(value = FigureNotFoundException.class)
    public ResponseEntity<Object> handlerUserNotFoundException(FigureNotFoundException exception) {
        ErrorFigureExists response = new ErrorFigureExists()
                .setErrorCode("FIGURE_NOT_FOUND")
                .setId(exception.getId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

package com.test.figures.exception;

import com.test.figures.exception.errors.ErrorMessageResponse;
import com.test.figures.exception.errors.ErrorsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handlerMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        ErrorsResponse errorResult = new ErrorsResponse().setCode("METHOD_ARGUMENT_NOT_VALID_EXCEPTION");
        exception.getBindingResult().getAllErrors()
                .forEach(objectError -> {
                    errorResult.setErrors(Arrays.asList(objectError.getDefaultMessage()));
                });
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> sqlExceptionHandler(SQLException exception) {
        ErrorMessageResponse response = new ErrorMessageResponse()
                .setCode("SQL_EXCEPTION")
                .setEntityName(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

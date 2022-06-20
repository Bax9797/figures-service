package com.test.figures.exception.userAdvice;

import com.test.figures.exception.ErrorUserExists;
import com.test.figures.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserNotFoundExceptionHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> handlerUserNotFoundException(UserNotFoundException exception) {
        ErrorUserExists response = new ErrorUserExists()
                .setCode("USER_NOT_FOUND")
                .setName(exception.getName());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

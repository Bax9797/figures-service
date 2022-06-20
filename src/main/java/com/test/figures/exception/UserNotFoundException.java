package com.test.figures.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    private String name;

    public UserNotFoundException(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

package com.test.figures.exception.errors;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ErrorMessageResponse {
    private String code;
    private String entityName;
}

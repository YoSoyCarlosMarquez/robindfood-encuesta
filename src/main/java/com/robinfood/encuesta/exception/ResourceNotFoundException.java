package com.robinfood.encuesta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 8885173172323095604L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
package com.robinfood.encuesta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 7990520604551351034L;

    public ForbiddenException(String message) {
        super(message);
    }

}

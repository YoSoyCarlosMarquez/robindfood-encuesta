package com.robinfood.encuesta.dto.constant;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class Message implements Serializable {

    private static final long serialVersionUID = 1652359669445648982L;
   
    @Value("${message.exception.general}")
    private String general;

    private String unauthorized;

    private String forbidden;

    @Value("${message.exception.nofound}")
    private String noFound;

    private String inconsistency;

    @Value("${message.exception.badrequest}")
    private String badRequest;

    private String methodNoAllow;

    private String missingServletRequestParameter;

    @Value("${message.exception.constraintviolation}")
    private String constraintViolation;

    private String argumentTypeMismatch;

    private String mediaTypeNoSupported;

    private String customNoData;

    private String nullField;

    private String noNullField;
    
    private String customExistData;

    public String getMessage(String message, Map<String, String> parameters) {
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String parameter = entry.getKey();
            String value = entry.getValue();
            message = message.replace(parameter, value);
        }
        return message;
    }

}

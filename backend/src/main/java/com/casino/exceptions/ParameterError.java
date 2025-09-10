package com.example.rule.exceptions;

import java.util.List;

public class ParameterError extends RuntimeException{
    List<String> errorCodes;

    public ParameterError (String message,List<String> errorCodes){
        super(message);
        this.errorCodes = errorCodes;
    }
}

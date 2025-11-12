package com.casino.exceptions;

import java.util.List;

public class IntegerOverflow extends RuntimeException{
    List<String> errorCodes;

    public IntegerOverflow (String message,List<String> errorCodes){
        super(message);
        this.errorCodes = errorCodes;
    }
}

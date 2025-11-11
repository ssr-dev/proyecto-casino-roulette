package com.casino.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String message;
    private String timeTamp;
    private List<String> errorCodes;

    public ErrorResponse(int status, String message,  List<String> errorCodes) {
        this.status = status;
        this.message = message;
        this.timeTamp = LocalDateTime.now().toString();
        this.errorCodes = errorCodes;
    }

    
}

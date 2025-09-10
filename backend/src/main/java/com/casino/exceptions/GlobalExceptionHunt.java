package com.example.rule.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.rule.helpers.ErrorCodes;

@RestControllerAdvice
public class GlobalExceptionHunt {

    @ExceptionHandler(IntegerOverflow.class)
    public ResponseEntity<ErrorResponse> hanndleInvalidRangeException(IntegerOverflow ex) {
        ErrorResponse errorResonse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.errorCodes);
        return new ResponseEntity<>(errorResonse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParameterError.class)
    public ResponseEntity<ErrorResponse> hanndleInvalidRangeException(ParameterError ex) {
        ErrorResponse errorResonse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.errorCodes);
        return new ResponseEntity<>(errorResonse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> hanndleInvalidRangeException(Exception ex) {
        ErrorResponse errorResonse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), List.of(ErrorCodes.UNEXPECTED_ERROR));
        return new ResponseEntity<>(errorResonse, HttpStatus.BAD_REQUEST);
    
    }
}

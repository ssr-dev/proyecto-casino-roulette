package com.casino.exceptions;

public class InvalidBetException extends RuntimeException {
    public InvalidBetException(String message) {
        super(message);
    }
}
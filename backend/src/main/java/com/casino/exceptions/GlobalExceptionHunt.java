package com.casino.exceptions;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.casino.helpers.ErrorCodes;

@RestControllerAdvice
public class GlobalExceptionHunt {

	@ExceptionHandler(IntegerOverflow.class)
	public ResponseEntity<ErrorResponse> hanndleInvalidRangeException(IntegerOverflow ex) {
		ErrorResponse errorResonse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.errorCodes);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorResonse);
	}

	@ExceptionHandler(ParameterError.class)
	public ResponseEntity<ErrorResponse> hanndleInvalidRangeException(ParameterError ex) {
		ErrorResponse errorResonse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.errorCodes);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorResonse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> hanndleInvalidRangeException(Exception ex) {
		ErrorResponse errorResonse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
				List.of(ErrorCodes.UNEXPECTED_ERROR));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorResonse);
	}
}

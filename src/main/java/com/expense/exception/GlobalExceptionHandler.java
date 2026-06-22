package com.expense.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	 @ExceptionHandler(UserAlreadyExistsException.class)
	    public ResponseEntity<?> handle(UserAlreadyExistsException ex) {

	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(Map.of(
	                        "message", ex.getMessage(),
	                        "status", 400
	                ));
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<?> handleGeneric(Exception ex) {

	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(Map.of(
	                        "message", "Something went wrong",
	                        "status", 500
	                ));
	    }
    
    @ExceptionHandler(
            MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(
            MethodArgumentNotValidException ex) {

        String error =
                ex.getBindingResult()
                  .getFieldError()
                  .getDefaultMessage();

        return ResponseEntity
                .badRequest()
                .body(error);
    }

}

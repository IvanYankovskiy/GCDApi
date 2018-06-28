package com.gcd.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class InvalidTaskExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(InvalidTaskRequestException.class)
    public final ResponseEntity<ApiError> handleUserNotFoundException(InvalidTaskRequestException ex, WebRequest request) {
    ApiError error = new ApiError(ex.getMessage());
    return new ResponseEntity<ApiError>(error, HttpStatus.BAD_REQUEST);
  }
    
}

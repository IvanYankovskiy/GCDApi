package com.gcd.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class InvalidTaskExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        BindingResult bindingResult= ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        ApiError error = new ApiError("Invalid request field \"" + fieldError.getField() + "\"." , 
                fieldError.getDefaultMessage());
        return new ResponseEntity<ApiError>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public final ResponseEntity<ApiError> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex, WebRequest request) {
        ApiError error = new ApiError("Unsupported value of request header \"Content-Type\"." , 
                "Use \"application/json\" value instead \"" + ex.getContentType() + "\".");
        return new ResponseEntity<ApiError>(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
    
}

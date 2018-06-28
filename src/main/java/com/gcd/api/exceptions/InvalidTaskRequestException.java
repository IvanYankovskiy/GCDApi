package com.gcd.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTaskRequestException extends RuntimeException {
    
    public InvalidTaskRequestException() {
        super();
    }

    public InvalidTaskRequestException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidTaskRequestException(final String message) {
        super(message);
    }

    public InvalidTaskRequestException(final Throwable cause) {
        super(cause);
    }
}

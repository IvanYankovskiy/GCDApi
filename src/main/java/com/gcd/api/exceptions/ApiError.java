package com.gcd.api.exceptions;

public class ApiError {
    private String status = "ERROR";
    private String message;
    private String details;

    public ApiError(String message, String details) {
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }   
}

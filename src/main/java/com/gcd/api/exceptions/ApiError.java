package com.gcd.api.exceptions;

public class ApiError {
    private String status = "ERROR";
    private String error;

    public ApiError(String error) {
        this.error = error;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    
}

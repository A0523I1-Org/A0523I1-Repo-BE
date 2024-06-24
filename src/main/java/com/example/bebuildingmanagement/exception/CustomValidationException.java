package com.example.bebuildingmanagement.exception;

public class CustomValidationException extends RuntimeException {
    private final String errorCode;

    public CustomValidationException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

package com.example.bebuildingmanagement.exception.authentication;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}

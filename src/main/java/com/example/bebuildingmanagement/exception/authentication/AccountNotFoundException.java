package com.example.bebuildingmanagement.exception.authentication;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
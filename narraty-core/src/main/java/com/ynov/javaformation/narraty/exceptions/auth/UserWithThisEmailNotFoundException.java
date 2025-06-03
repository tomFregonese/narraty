package com.ynov.javaformation.narraty.exceptions.auth;

public class UserWithThisEmailNotFoundException extends RuntimeException {
    public UserWithThisEmailNotFoundException(String message) {
        super(message);
    }
}

package com.ynov.javaformation.narraty.exceptions.auth;

public class UsernameAlreadyExistException extends RuntimeException {
    public UsernameAlreadyExistException(String message) {
        super(message);
    }
}
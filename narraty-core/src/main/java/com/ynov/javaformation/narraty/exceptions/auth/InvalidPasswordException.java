package com.ynov.javaformation.narraty.exceptions.auth;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}

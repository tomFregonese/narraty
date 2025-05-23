package com.ynov.javaformation.narraty.exceptions.auth;

public class PasswordDoesNotMeetRequirementException extends RuntimeException {
    public PasswordDoesNotMeetRequirementException(String message) {
        super(message);
    }
}

package com.ynov.javaformation.narraty.exceptions.story;

public class TaleStatusWithThisIdDoesNotExistException extends RuntimeException {
    public TaleStatusWithThisIdDoesNotExistException(String message) {
        super(message);
    }
}

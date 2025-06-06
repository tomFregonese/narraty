package com.ynov.javaformation.narraty.exceptions.story;

public class SceneStatusWithThisIdDoesNotExistException extends RuntimeException {
    public SceneStatusWithThisIdDoesNotExistException(String message) {
        super(message);
    }
}

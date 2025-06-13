package com.ynov.javaformation.narraty.exceptions.story;

import com.ynov.javaformation.narraty.models.TaleErrors;

import java.util.Collection;

public class UnprocessableTaleException extends RuntimeException {

    public final Collection<TaleErrors> taleErrors;

    public UnprocessableTaleException(String message, Collection<TaleErrors> taleErrors) {
        super(message);
        this.taleErrors = taleErrors;
    }
}

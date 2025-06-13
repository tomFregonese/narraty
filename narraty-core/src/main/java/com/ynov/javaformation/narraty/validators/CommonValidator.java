package com.ynov.javaformation.narraty.validators;

public interface CommonValidator<Input, Output> {
    Output validate(Input input);
}

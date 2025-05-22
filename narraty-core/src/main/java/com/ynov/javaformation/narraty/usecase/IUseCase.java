package com.ynov.javaformation.narraty.usecase;

public interface IUseCase<Input, Output> {
    Output handle(Input input) throws Exception;
}
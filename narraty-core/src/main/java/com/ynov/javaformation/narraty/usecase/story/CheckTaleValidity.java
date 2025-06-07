package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.TaleErrors;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CheckTaleValidity implements IUseCase<Tale, Collection<TaleErrors>> {

    public Collection<TaleErrors> handle(Tale tale) {

        return null;

    }

}

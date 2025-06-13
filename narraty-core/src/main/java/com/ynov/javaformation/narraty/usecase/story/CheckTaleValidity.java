package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.TaleErrors;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import com.ynov.javaformation.narraty.validators.tale.TaleValidators;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CheckTaleValidity implements IUseCase<Tale, Collection<TaleErrors>> {

    private final TaleValidators taleValidators;

    public CheckTaleValidity(TaleValidators taleValidators) {
        this.taleValidators = taleValidators;
    }

    public Collection<TaleErrors> handle(Tale tale) {

        if (tale == null) {
            throw new IllegalArgumentException("Tale cannot be null");
        }

        Collection<TaleErrors> errors = taleValidators.validate(tale);

        if (errors.isEmpty()) {
            return null;
        } else {
            return errors;
        }

    }

}

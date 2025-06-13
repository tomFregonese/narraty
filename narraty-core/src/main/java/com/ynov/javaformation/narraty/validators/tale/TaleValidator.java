package com.ynov.javaformation.narraty.validators.tale;

import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.TaleErrors;
import com.ynov.javaformation.narraty.validators.CommonValidator;

import java.util.Optional;

public interface TaleValidator extends CommonValidator<Tale, Optional<TaleErrors>> {
    Optional<TaleErrors> validate(Tale tale);
}

package com.ynov.javaformation.narraty.validators.tale;

import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.TaleErrors;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AtLeastFourScenesValidator implements TaleValidator {

    public Optional<TaleErrors> validate(Tale tale) {
        if (tale.scenes == null || tale.scenes.size() < 4) {
            return Optional.of(TaleErrors.HasNotAtLeastFourScenes);
        }
        return Optional.empty();
    }

}

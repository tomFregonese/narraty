package com.ynov.javaformation.narraty.validators.tale;

import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.TaleErrors;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NoChoicePointsToSelfValidator implements TaleValidator {

    public Optional<TaleErrors> validate(Tale tale) {
        boolean valid = tale.scenes.stream()
            .flatMap(s -> s.choices.stream())
            .noneMatch(c -> c.sceneId.equals(c.nextSceneId));
        return valid ? Optional.empty() : Optional.of(TaleErrors.ChoicePointsToSelf);
    }
}
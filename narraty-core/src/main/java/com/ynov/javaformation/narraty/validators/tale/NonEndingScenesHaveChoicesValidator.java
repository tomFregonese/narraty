package com.ynov.javaformation.narraty.validators.tale;

import com.ynov.javaformation.narraty.models.SceneStatus;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.TaleErrors;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NonEndingScenesHaveChoicesValidator implements TaleValidator {

    public Optional<TaleErrors> validate(Tale tale) {
        boolean allHaveChoices = tale.scenes.stream()
            .filter(s -> s.status == SceneStatus.Start || s.status == SceneStatus.Default)
            .allMatch(s -> !s.choices.isEmpty());
        return allHaveChoices ? Optional.empty() : Optional.of(TaleErrors.NoChoiceInNonEndingScene);
    }
}
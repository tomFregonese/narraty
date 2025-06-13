package com.ynov.javaformation.narraty.validators.tale;

import com.ynov.javaformation.narraty.models.SceneStatus;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.TaleErrors;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EndingScenesHaveNoChoicesValidator implements TaleValidator {

    public Optional<TaleErrors> validate(Tale tale) {
        boolean noneHaveChoices = tale.scenes.stream()
            .filter(s -> s.status == SceneStatus.Win || s.status == SceneStatus.Lose)
            .allMatch(s -> s.choices.isEmpty());
        return noneHaveChoices ? Optional.empty() : Optional.of(TaleErrors.ChoicesInEndingScene);
    }
}
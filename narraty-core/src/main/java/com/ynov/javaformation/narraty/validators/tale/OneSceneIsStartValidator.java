package com.ynov.javaformation.narraty.validators.tale;

import com.ynov.javaformation.narraty.models.SceneStatus;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.TaleErrors;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OneSceneIsStartValidator implements TaleValidator {

    public Optional<TaleErrors> validate(Tale tale) {
        long count = tale.scenes.stream().filter(s -> s.status == SceneStatus.Start).count();
        return count == 1 ? Optional.empty() : Optional.of(TaleErrors.NoStartScene);
    }
}
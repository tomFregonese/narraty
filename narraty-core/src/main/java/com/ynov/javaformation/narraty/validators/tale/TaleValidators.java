package com.ynov.javaformation.narraty.validators.tale;

import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.TaleErrors;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class TaleValidators {

    private final List<TaleValidator> validators = new ArrayList<>();

    public TaleValidators(
            AtLeastFourScenesValidator atLeastFourScenesValidator,
            AtLeastOneWinSceneValidator atLeastOneWinSceneValidator,
            AtLeastOneLoseSceneValidator atLeastOneLoseSceneValidator,
            OneSceneIsStartValidator oneSceneIsStartValidator,
            ChoicePointsToExistingSceneValidator choicePointsToExistingSceneValidator,
            EndingScenesHaveNoChoicesValidator endingScenesHaveNoChoicesValidator,
            NoChoicePointsToSelfValidator noChoicePointsToSelfValidator,
            NoInaccessibleSceneValidator noInaccessibleSceneValidator,
            NonEndingScenesHaveChoicesValidator nonEndingScenesHaveChoicesValidator
    ) {
        this.validators.add(atLeastFourScenesValidator);
        this.validators.add(atLeastOneWinSceneValidator);
        this.validators.add(atLeastOneLoseSceneValidator);
        this.validators.add(oneSceneIsStartValidator);
        this.validators.add(choicePointsToExistingSceneValidator);
        this.validators.add(endingScenesHaveNoChoicesValidator);
        this.validators.add(noChoicePointsToSelfValidator);
        this.validators.add(noInaccessibleSceneValidator);
        this.validators.add(nonEndingScenesHaveChoicesValidator);
    }

    public Collection<TaleErrors> validate(Tale tale) {
        Collection<TaleErrors> errors = new ArrayList<>();
        for (TaleValidator validator : validators) {
            Optional<TaleErrors> error = validator.validate(tale);
            error.ifPresent(errors::add);
        }
        return errors;
    }

}

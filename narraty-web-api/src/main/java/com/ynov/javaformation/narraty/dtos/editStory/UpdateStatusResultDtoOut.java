package com.ynov.javaformation.narraty.dtos.editStory;

import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.TaleErrors;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;


@Getter
@AllArgsConstructor
public class UpdateStatusResultDtoOut {

    public EditTaleDtoOut tale;

    public TaleErrorsDtoOut errors;

    public static UpdateStatusResultDtoOut mapToDto(Tale tale) {
        return new UpdateStatusResultDtoOut(
                EditTaleDtoOut.mapToDto(tale),
                null
        );
    }

    public static UpdateStatusResultDtoOut mapToDto(Collection<TaleErrors> errors) {
        return new UpdateStatusResultDtoOut(
                null,
                TaleErrorsDtoOut.mapToDto(errors)
        );
    }

}

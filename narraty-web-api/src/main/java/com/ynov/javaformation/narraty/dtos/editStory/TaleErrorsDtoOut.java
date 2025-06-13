package com.ynov.javaformation.narraty.dtos.editStory;

import com.ynov.javaformation.narraty.models.TaleErrors;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class TaleErrorsDtoOut {

    Collection<String> errorsMessages;


    public static TaleErrorsDtoOut mapToDto(Collection<TaleErrors> errors) {
        return new TaleErrorsDtoOut(
                errors.stream()
                        .map(TaleErrors::getMessage)
                        .toList()
        );
    }

}

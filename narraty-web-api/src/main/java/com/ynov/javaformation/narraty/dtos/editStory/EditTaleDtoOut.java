package com.ynov.javaformation.narraty.dtos.editStory;

import com.ynov.javaformation.narraty.models.Tale;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class EditTaleDtoOut {

    UUID id;

    String ttl;

    String dsc;

    LocalDateTime crtAt;

    LocalDateTime updAt;

    UUID autrId;

    Collection<EditSceneDtoOut> scns;

    int plyCnt;

    int stts;


    public static EditTaleDtoOut mapToDto(Tale tale) {
        return new EditTaleDtoOut(
                tale.id,
                tale.title,
                tale.description,
                tale.createdAt,
                tale.updatedAt,
                tale.authorId,
                tale.scenes == null ? null :
                    tale.scenes.stream()
                        .map(EditSceneDtoOut::mapToDto)
                        .toList(),
                tale.playCount,
                tale.status.ordinal()
        );
    }

}
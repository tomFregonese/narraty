package com.ynov.javaformation.narraty.dtos.publicStory;

import com.ynov.javaformation.narraty.models.Tale;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
public class PublicTaleDtoOut {

    public UUID id;

    public String ttl;

    public String desc;

    public LocalDateTime crtdAt;

    public LocalDateTime updtAt;

    public UUID autrId;

    public int plyCnt;


    public static PublicTaleDtoOut mapToDto(Tale tale) {
        return new PublicTaleDtoOut(
                tale.id,
                tale.title,
                tale.description,
                tale.createdAt,
                tale.updatedAt,
                tale.authorId,
                tale.playCount
        );
    }

}

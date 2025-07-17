package com.ynov.javaformation.narraty.dtos.auth;

import com.ynov.javaformation.narraty.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserDtoOut {

    UUID id;

    String usrnm;

    String eml;

    LocalDateTime crtdAt;

    LocalDateTime updtdAt;

    int exPnts;


    public static UserDtoOut mapToDto(User user) {
        return new UserDtoOut(
            user.id,
            user.username,
            user.email,
            user.createdAt,
            user.updatedAt,
            user.experiencePoints
        );
    }

}

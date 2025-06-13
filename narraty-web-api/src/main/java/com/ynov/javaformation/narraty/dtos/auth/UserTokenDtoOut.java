package com.ynov.javaformation.narraty.dtos.auth;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UserTokenDtoOut {

    public UUID userToken;


    public static UserTokenDtoOut mapToDto(UUID userToken) {
        return new UserTokenDtoOut(userToken);
    }

}

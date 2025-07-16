package com.ynov.javaformation.narraty.controllers;

import com.ynov.javaformation.narraty.dtos.auth.UserDtoOut;
import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.usecase.user.GetUserInfoUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
@Tag(name = "User")
@RequestMapping("/user")
public class UserController {

    private final GetUserInfoUseCase getUserInfoUseCase;

    @Autowired
    public UserController(GetUserInfoUseCase getUserInfouseCase) {
        this.getUserInfoUseCase = getUserInfouseCase;
    }

    @GetMapping("/user-info")
    public ResponseEntity<UserDtoOut> getUserInfo() {
        try {
            User user = getUserInfoUseCase.handle(null);
            UserDtoOut userDto = UserDtoOut.mapToDto(user);
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

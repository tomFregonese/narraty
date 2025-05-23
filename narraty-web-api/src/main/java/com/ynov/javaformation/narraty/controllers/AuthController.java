package com.ynov.javaformation.narraty.controllers;

import com.ynov.javaformation.narraty.models.SignUpCredentials;
import com.ynov.javaformation.narraty.usecase.auth.SignUpUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SignUpUseCase signUpUseCase;

    @Autowired
    public AuthController(SignUpUseCase signUp) {
        this.signUpUseCase = signUp;
    }

    @PostMapping("/signup")
    public ResponseEntity<UUID> signUp(@RequestBody SignUpCredentials credentials) {
        try {
            UUID sessionId = signUpUseCase.handle(credentials);
            return ResponseEntity.status(HttpStatus.CREATED).body(sessionId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

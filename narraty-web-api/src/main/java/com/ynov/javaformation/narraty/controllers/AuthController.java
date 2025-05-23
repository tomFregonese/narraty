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
            switch (e.getClass().getSimpleName()) {
                case "UsernameAlreadyExistException"://TODO I'm blocked to detect the kind of exception
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(null);//TODO I'm blocked to add a message cuz it's not a UUID.
                case "EmailAlreadyExistsException":
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
                case "PasswordDoesNotMeetRequirementException":
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                default:
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

}

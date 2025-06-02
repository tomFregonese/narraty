package com.ynov.javaformation.narraty.controllers;

import com.ynov.javaformation.narraty.models.SignInCredentials;
import com.ynov.javaformation.narraty.models.SignUpCredentials;
import com.ynov.javaformation.narraty.models.UserCredentialsOut;
import com.ynov.javaformation.narraty.security.Authorize;
import com.ynov.javaformation.narraty.usecase.auth.SignInUseCase;
import com.ynov.javaformation.narraty.usecase.auth.SignUpUseCase;
import com.ynov.javaformation.narraty.usecase.auth.TestSignedInUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SignUpUseCase signUpUseCase;
    private final SignInUseCase signInUseCase;
    private final TestSignedInUserUseCase testSignedInUserUseCase;

    @Autowired
    public AuthController(SignUpUseCase signUp, SignInUseCase signInUseCase, TestSignedInUserUseCase testSignedInUserUseCase) {
        this.signUpUseCase = signUp;
        this.signInUseCase = signInUseCase;
        this.testSignedInUserUseCase = testSignedInUserUseCase;
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

    @PostMapping("/signin")
    public ResponseEntity<UUID> signIn(@RequestBody SignInCredentials credentials) {
        try {
            UUID sessionId = signInUseCase.handle(credentials);
            return ResponseEntity.status(HttpStatus.OK).body(sessionId);
        } catch (Exception e) {
            switch (e.getClass().getSimpleName()) {
                case "UserWithThisEmailNotFoundException":
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                case "InvalidPasswordException":
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
                default:
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @Authorize
    @GetMapping("/test-signed-in-user")
    public ResponseEntity<UserCredentialsOut> testSignedInUser() {
        try {

            UserCredentialsOut userCredentialsOut = testSignedInUserUseCase.handle(null);
            return ResponseEntity.status(HttpStatus.OK).body(userCredentialsOut);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

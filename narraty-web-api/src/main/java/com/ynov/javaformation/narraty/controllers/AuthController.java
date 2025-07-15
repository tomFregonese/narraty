package com.ynov.javaformation.narraty.controllers;

import com.ynov.javaformation.narraty.exceptions.auth.*;
import com.ynov.javaformation.narraty.models.SignInCredentials;
import com.ynov.javaformation.narraty.models.SignUpCredentials;
import com.ynov.javaformation.narraty.models.UserCredentialsOut;
import com.ynov.javaformation.narraty.security.Authorize;
import com.ynov.javaformation.narraty.usecase.auth.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Transactional
@RestController
@Tag(name = "Authentication")
@RequestMapping("/auth")
public class AuthController {

    private final SignUpUseCase signUpUseCase;
    private final SignInUseCase signInUseCase;
    private final LogoutUseCase logoutUseCase;
    private final TestSignedInUserUseCase testSignedInUserUseCase;
    private final ClearExpiredSessionsUseCase clearExpiredSessionsUseCase;

    @Autowired
    public AuthController(SignUpUseCase signUp, SignInUseCase signInUseCase, LogoutUseCase logoutUseCase,
                          TestSignedInUserUseCase testSignedInUserUseCase, ClearExpiredSessionsUseCase clearExpiredSessionsUseCase) {
        this.signUpUseCase = signUp;
        this.signInUseCase = signInUseCase;
        this.logoutUseCase = logoutUseCase;
        this.testSignedInUserUseCase = testSignedInUserUseCase;
        this.clearExpiredSessionsUseCase = clearExpiredSessionsUseCase;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpCredentials credentials) {
        try {

            UUID sessionId = signUpUseCase.handle(credentials);
            return ResponseEntity.status(HttpStatus.CREATED).body(sessionId.toString());

        } catch (UsernameAlreadyExistException | EmailAlreadyExistsException e) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        } catch (InvalidEmailException | PasswordDoesNotMeetRequirementException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody SignInCredentials credentials) {
        try {

            UUID sessionId = signInUseCase.handle(credentials);
            return ResponseEntity.status(HttpStatus.OK).body(sessionId.toString());

        } catch (UserWithThisEmailNotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (InvalidPasswordException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

    @Authorize
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        try {
            logoutUseCase.handle(null);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/clear-expired-sessions")
    public ResponseEntity<Void> clearExpiredSessions() {
        try {
            // This endpoint is for testing purposes and should not be exposed in production
            // It is assumed that the ClearExpiredSessionsUseCase is scheduled to run periodically
            clearExpiredSessionsUseCase.handle(null);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

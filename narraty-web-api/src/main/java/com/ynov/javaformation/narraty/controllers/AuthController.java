package com.ynov.javaformation.narraty.controllers;

import com.ynov.javaformation.narraty.exceptions.auth.*;
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

}

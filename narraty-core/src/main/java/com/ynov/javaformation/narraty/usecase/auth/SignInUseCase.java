package com.ynov.javaformation.narraty.usecase.auth;

import com.ynov.javaformation.narraty.exceptions.auth.*;
import com.ynov.javaformation.narraty.interfaces.daos.UserDao;
import com.ynov.javaformation.narraty.interfaces.services.IPasswordService;
import com.ynov.javaformation.narraty.models.SignInCredentials;
import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.interfaces.services.ISessionService;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SignInUseCase implements IUseCase<SignInCredentials, UUID> {

    private final UserDao userDao;
    private final ISessionService sessionService;
    private final IPasswordService passwordService;

    public SignInUseCase(UserDao userDao, ISessionService sessionService, IPasswordService passwordService) {
        this.userDao = userDao;
        this.sessionService = sessionService;
        this.passwordService = passwordService;
    }

    public UUID handle(SignInCredentials credentials) throws Exception {
        try {

            User user = userDao.findByEmail(credentials.email).orElseThrow(() -> new UserWithThisEmailNotFoundException("User with this email not " +
                    "found"));

            if (!passwordService.verifyPassword(credentials.password, user.passwordHash)) {
                throw new InvalidPasswordException("Passwords do not match");
            }

            return sessionService.createSession(user);

        } catch (Exception e) {
            throw new Exception("Error signing up: " + e.getMessage());
        }
    }

}

package com.ynov.javaformation.narraty.usecase.auth;

import com.ynov.javaformation.narraty.exceptions.auth.*;
import com.ynov.javaformation.narraty.irepositories.UserDao;
import com.ynov.javaformation.narraty.models.SignInCredentials;
import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.sessions.ISessionService;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SignInUseCase implements IUseCase<SignInCredentials, UUID> {

    private final UserDao userDao;
    private final ISessionService sessionService;

    public SignInUseCase(UserDao userDao, ISessionService sessionService) {
        this.userDao = userDao;
        this.sessionService = sessionService;
    }

    public UUID handle(SignInCredentials credentials) throws Exception {
        try {

            User user = userDao.findByEmail(credentials.email).orElseThrow(() -> new UserWithThisEmailNotFoundException("User with this email not " +
                    "found"));

            if (!user.passwordHash.equals(credentials.password)) { // TODO Use the method made for this when the
                throw new InvalidPasswordException("Passwords do not match"); // PasswordService will be implemented
            }

            return sessionService.createSession(user);

        } catch (Exception e) {
            throw new Exception("Error signing up: " + e.getMessage());
        }
    }

}

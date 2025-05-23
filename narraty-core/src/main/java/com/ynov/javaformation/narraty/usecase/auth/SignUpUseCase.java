package com.ynov.javaformation.narraty.usecase.auth;

import com.ynov.javaformation.narraty.irepositories.UserDao;
import com.ynov.javaformation.narraty.models.SignUpCredentials;
import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.sessions.ISessionService;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SignUpUseCase implements IUseCase<SignUpCredentials, UUID> {

    private final UserDao userDao;
    private final ISessionService sessionService;

    public SignUpUseCase(UserDao userDao, ISessionService sessionService) {
        this.userDao = userDao;
        this.sessionService = sessionService;
    }

    public UUID handle(SignUpCredentials credentials) throws Exception {
        try {

            LocalDateTime now = LocalDateTime.now();

            User userToSave = User.builder()
                    .username(credentials.username)
                    .email(credentials.email)
                    .passwordHash(credentials.password) // TODO hash password here
                    .experiencePoints(0)
                    .build();

            User newUser = userDao.save(userToSave);

            return sessionService.createSession(newUser);

        } catch (Exception e) {
            throw new Exception("Error signing up: " + e.getMessage());
        }
    }

}

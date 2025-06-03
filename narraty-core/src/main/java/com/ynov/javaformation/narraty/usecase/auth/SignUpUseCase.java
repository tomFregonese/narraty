package com.ynov.javaformation.narraty.usecase.auth;

import com.ynov.javaformation.narraty.exceptions.auth.EmailAlreadyExistsException;
import com.ynov.javaformation.narraty.exceptions.auth.InvalidEmailException;
import com.ynov.javaformation.narraty.exceptions.auth.PasswordDoesNotMeetRequirementException;
import com.ynov.javaformation.narraty.exceptions.auth.UsernameAlreadyExistException;
import com.ynov.javaformation.narraty.interfaces.daos.UserDao;
import com.ynov.javaformation.narraty.interfaces.services.IPasswordService;
import com.ynov.javaformation.narraty.models.SignUpCredentials;
import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.interfaces.services.ISessionService;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import com.ynov.javaformation.narraty.validators.EmailValidator;
import com.ynov.javaformation.narraty.validators.PasswordValidator;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SignUpUseCase implements IUseCase<SignUpCredentials, UUID> {

    private final UserDao userDao;
    private final ISessionService sessionService;
    private final IPasswordService passwordService;

    public SignUpUseCase(UserDao userDao, ISessionService sessionService, IPasswordService passwordService) {
        this.userDao = userDao;
        this.sessionService = sessionService;
        this.passwordService = passwordService;
    }

    public UUID handle(SignUpCredentials credentials) throws Exception {
        try {

            if (!EmailValidator.isValid(credentials.email)) throw new InvalidEmailException("Invalid email format");

            if (userDao.findByUsername(credentials.username).isPresent()) throw new UsernameAlreadyExistException("Username already exists");

            if (userDao.findByEmail(credentials.email).isPresent()) throw new EmailAlreadyExistsException("Email already exists");

            if (!PasswordValidator.IsSecure(credentials.password)) throw new PasswordDoesNotMeetRequirementException("Password does not meet security requirements");

            User userToSave = User.builder()
                    .username(credentials.username)
                    .email(credentials.email)
                    .passwordHash(passwordService.hashPassword(credentials.password))
                    .experiencePoints(0)
                    .build();

            User newUser = userDao.save(userToSave);

            return sessionService.createSession(newUser);

        } catch (Exception e) {
            throw new Exception("Error signing up: " + e.getMessage());
        }
    }

}

package com.ynov.javaformation.narraty.usecase.auth;

import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.models.UserCredentialsOut;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import org.springframework.stereotype.Service;

@Service
public class TestSignedInUserUseCase implements IUseCase<Void, UserCredentialsOut> {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    public TestSignedInUserUseCase(GetAuthenticatedUserUseCase getAuthenticatedUserUseCase) {
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
    }

    public UserCredentialsOut handle(Void unused) throws Exception {
        try {

            User user = getAuthenticatedUserUseCase.handle(null);

            if (user == null) throw new Exception("No user is currently signed in.");

            UserCredentialsOut userCredentialsOut = new UserCredentialsOut();
            userCredentialsOut.id = user.id;
            userCredentialsOut.username = user.username;
            userCredentialsOut.email = user.email;
            userCredentialsOut.updatedAt = user.updatedAt;

            return userCredentialsOut;

        } catch (Exception e) {
            throw new Exception("Error trying to get the connected user: " + e.getMessage());
        }
    }

}

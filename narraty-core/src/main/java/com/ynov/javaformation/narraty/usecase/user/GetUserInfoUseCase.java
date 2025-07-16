package com.ynov.javaformation.narraty.usecase.user;

import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import com.ynov.javaformation.narraty.usecase.auth.GetAuthenticatedUserUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetUserInfoUseCase implements IUseCase<Void, User> {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    public GetUserInfoUseCase(GetAuthenticatedUserUseCase getAuthenticatedUserUseCase) {
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
    }

    public User handle(Void unused) throws Exception {
        try {

            User user = getAuthenticatedUserUseCase.handle(null);

            if (user == null) throw new Exception("No user is currently signed in.");

            return user;

        } catch (Exception e) {
            throw new Exception("Error trying to get the connected user: " + e.getMessage());
        }
    }

}

package com.ynov.javaformation.narraty.usecase.auth;

import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GetAuthenticatedUserUseCase implements IUseCase<Void, User> {

    /**
     * Gets the authenticated user for the request.
     *
     * @return The authenticated user.
     */
    public User handle(Void unused) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();

    }

}

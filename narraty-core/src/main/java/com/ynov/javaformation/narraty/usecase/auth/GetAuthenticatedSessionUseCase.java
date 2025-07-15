package com.ynov.javaformation.narraty.usecase.auth;

import com.ynov.javaformation.narraty.usecase.IUseCase;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetAuthenticatedSessionUseCase implements IUseCase<Void, UUID> {

    /**
     * Gets the session that have been used to authenticate the user for the request.
     *
     * @return The sessionId.
     */
    public UUID handle(Void unused) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (UUID) auth.getDetails();

    }

}

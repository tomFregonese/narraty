package com.ynov.javaformation.narraty.usecase.auth;

import com.ynov.javaformation.narraty.interfaces.services.ISessionService;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LogoutUseCase implements IUseCase<Void, Void> {

    private final GetAuthenticatedSessionUseCase getAuthenticatedSessionUseCase;
    private final ISessionService sessionService;

    public LogoutUseCase(GetAuthenticatedSessionUseCase getAuthenticatedSessionUseCase,
                         ISessionService sessionService) {
        this.getAuthenticatedSessionUseCase = getAuthenticatedSessionUseCase;
        this.sessionService = sessionService;
    }

    public Void handle(Void unused) {

        UUID sessionId = getAuthenticatedSessionUseCase.handle(unused);

        sessionService.invalidateSession(sessionId);

        return null;

    }

}

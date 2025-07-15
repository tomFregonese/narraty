package com.ynov.javaformation.narraty.usecase.auth;

import com.ynov.javaformation.narraty.interfaces.services.ISessionService;
import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LogoutAnyOtherSessionsUseCase implements IUseCase<Void, Void> {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final GetAuthenticatedSessionUseCase getAuthenticatedSessionUseCase;
    private final ISessionService sessionService;

    public LogoutAnyOtherSessionsUseCase(GetAuthenticatedSessionUseCase getAuthenticatedSessionUseCase,
                                         GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
                                         ISessionService sessionService) {
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.getAuthenticatedSessionUseCase = getAuthenticatedSessionUseCase;
        this.sessionService = sessionService;
    }

    public Void handle(Void unused) {

        User user = getAuthenticatedUserUseCase.handle(unused);
        UUID currentSessionId = getAuthenticatedSessionUseCase.handle(unused);

        sessionService.invalidateUserSessionsExceptCurrent(user, currentSessionId);

        return null;

    }

}

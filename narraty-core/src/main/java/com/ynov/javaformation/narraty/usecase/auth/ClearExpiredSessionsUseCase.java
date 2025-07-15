package com.ynov.javaformation.narraty.usecase.auth;

import com.ynov.javaformation.narraty.interfaces.daos.SessionDao;
import com.ynov.javaformation.narraty.interfaces.services.ISessionService;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ClearExpiredSessionsUseCase implements IUseCase<Void, Void> {

    private final ISessionService sessionService;
    private final SessionDao sessionDao;

    public ClearExpiredSessionsUseCase(ISessionService sessionService, SessionDao sessionDao) {
        this.sessionService = sessionService;
        this.sessionDao = sessionDao;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public Void handle(Void unused) {

        sessionDao.deleteExpiredSessions();

        return null;

    }

}

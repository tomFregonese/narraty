package com.ynov.javaformation.narraty.data.repositories;

import com.ynov.javaformation.narraty.data.entities.SessionEntity;
import com.ynov.javaformation.narraty.interfaces.daos.SessionDao;
import com.ynov.javaformation.narraty.models.Session;
import com.ynov.javaformation.narraty.validators.auth.SessionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class SessionJpaRepositoryAdapter implements SessionDao {

    @Autowired
    private SessionJpaRepository repository;

    public Session save(Session session) {
        SessionEntity sessionEntity = SessionEntity.mapToEntity(session);
        return repository.save(sessionEntity).mapToDomain();
    }

    public Optional<Session> findByToken(UUID id) {
        Optional<SessionEntity> sessionEntity = repository.findByToken(id);
        return sessionEntity.map(SessionEntity::mapToDomain);
    }

    public void delete(UUID sessionToken) { repository.deleteById(sessionToken); }

    public void deleteSessionsFromAUserExceptOne(UUID userId, UUID sessionToKeep) {
        repository.deleteSessionsFromAUserExceptOne(userId, sessionToKeep);
    }

    public void deleteExpiredSessions() {
        repository.deleteExpiredSessions(LocalDateTime.now().minusDays(SessionValidator.getSessionExpirationDays()));
    }

}

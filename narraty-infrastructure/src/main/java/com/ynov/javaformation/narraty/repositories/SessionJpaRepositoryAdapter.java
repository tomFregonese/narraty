package com.ynov.javaformation.narraty.repositories;

import com.ynov.javaformation.narraty.entities.SessionEntity;
import com.ynov.javaformation.narraty.irepositories.SessionDao;
import com.ynov.javaformation.narraty.models.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public void deleteAllByUserId(UUID userId) {
        repository.deleteAllByUserId(userId);
    }

}

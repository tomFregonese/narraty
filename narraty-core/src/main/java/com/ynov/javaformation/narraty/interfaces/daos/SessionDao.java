package com.ynov.javaformation.narraty.interfaces.daos;


import com.ynov.javaformation.narraty.models.Session;

import java.util.Optional;
import java.util.UUID;

public interface SessionDao {

    Session save(Session session);

    void delete(UUID sessionToken);

    Optional<Session> findByToken(UUID id);

    void deleteAllByUserId(UUID userId);

}

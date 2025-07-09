package com.ynov.javaformation.narraty.interfaces.daos;

import com.ynov.javaformation.narraty.models.UserTalePlay;

import java.util.Optional;
import java.util.UUID;

public interface UserTalePlayDao {

    UserTalePlay save(UserTalePlay utp);

    Optional<UserTalePlay> findByUserAndTaleId(UUID userId, UUID taleId);

    void delete(UserTalePlay utp);

}

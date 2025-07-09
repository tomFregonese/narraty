package com.ynov.javaformation.narraty.data.repositories;

import com.ynov.javaformation.narraty.data.entities.UserTalePlayEntity;
import com.ynov.javaformation.narraty.interfaces.daos.UserTalePlayDao;
import com.ynov.javaformation.narraty.models.UserTalePlay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserTalePlayJpaRepositoryAdapter implements UserTalePlayDao {

    @Autowired
    private UserTalePlayJpaRepository repository;


    public UserTalePlay save(UserTalePlay user) {
        UserTalePlayEntity utpEntity = UserTalePlayEntity.mapToEntity(user);
        return repository.save(utpEntity).mapToDomain();
    }

    public Optional<UserTalePlay> findByUserAndTaleId(UUID userId, UUID taleId) {
        Optional<UserTalePlayEntity> utpEntity = repository.findByUserIdAndTaleId(userId, taleId);
        return utpEntity.map(UserTalePlayEntity::mapToDomain);
    }

    public void delete(UserTalePlay utp) {
        repository.delete(UserTalePlayEntity.mapToEntity(utp));
    }
}

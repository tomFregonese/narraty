package com.ynov.javaformation.narraty.repositories;

import com.ynov.javaformation.narraty.entities.UserEntity;
import com.ynov.javaformation.narraty.irepositories.UserDao;
import com.ynov.javaformation.narraty.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserJpaRepositoryAdapter implements UserDao {

    @Autowired
    private UserJpaRepository repository;


    public User save(User user) {
        UserEntity userEntity = UserEntity.mapToEntity(user);
        return repository.save(userEntity).mapToDomain();
    }

    public Optional<User> findById(UUID userId) {
        Optional<UserEntity> userEntity = repository.findById(userId);
        return userEntity.map(UserEntity::mapToDomain);
    }

    public Optional<User> findByUsername(String username) {
        Optional<UserEntity> userEntity = repository.findByUsername(username);
        return userEntity.map(UserEntity::mapToDomain);
    }

    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> userEntity = repository.findByEmail(email);
        return userEntity.map(UserEntity::mapToDomain);
    }

    public void deleteById(UUID userId) { repository.deleteById(userId); }
}

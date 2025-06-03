package com.ynov.javaformation.narraty.data.entities;

import com.ynov.javaformation.narraty.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(nullable = false, unique = true)
    public String username;

    @Column(nullable = false, unique = true)
    public String email;

    @Column(nullable = false)
    public String passwordHash;

    public int experiencePoints;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (this.createdAt == null) this.createdAt = now;
        if (this.updatedAt == null) this.updatedAt = now;
    }



    public User mapToDomain() {
        return new User(
                this.id,
                this.username,
                this.email,
                this.passwordHash,
                this.experiencePoints,
                this.updatedAt,
                this.createdAt
        );
    }

    public static UserEntity mapToEntity(User user) {
        return new UserEntity(
                user.id,
                user.username,
                user.email,
                user.passwordHash,
                user.experiencePoints,
                user.updatedAt,
                user.createdAt
        );
    }

}
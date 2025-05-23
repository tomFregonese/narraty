package com.ynov.javaformation.narraty.entities;

import com.ynov.javaformation.narraty.models.Session;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sessions")
@NoArgsConstructor
@AllArgsConstructor
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID token;

    @Column(name = "user_id", nullable = false)
    public UUID userId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    public Session mapToDomain() {
        return new Session(
                this.token,
                this.userId,
                this.createdAt
        );
    }

    public static SessionEntity mapToEntity(Session session) {
        return new SessionEntity(
                session.token,
                session.userId,
                session.createdAt
        );
    }

}

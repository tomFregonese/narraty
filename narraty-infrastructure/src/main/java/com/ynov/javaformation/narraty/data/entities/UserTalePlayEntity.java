package com.ynov.javaformation.narraty.data.entities;

import com.ynov.javaformation.narraty.models.UserTalePlay;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_tale_plays")
@NoArgsConstructor
@AllArgsConstructor
public class UserTalePlayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity user;

    @ManyToOne
    @JoinColumn(name = "tale_id", nullable = false)
    public TaleEntity tale;

    @Column(name = "play_count")
    public int playCount;

    @Column(name = "current_scene_id")
    public UUID currentSceneId;

    @Column(name = "last_played_at")
    public LocalDateTime lastPlayedAt;


    public UserTalePlay mapToDomain() {
        return new UserTalePlay(
                this.id,
                this.user.mapToDomain(),
                this.tale.mapToDomain(),
                this.playCount,
                this.currentSceneId,
                this.lastPlayedAt
        );
    }

    public static UserTalePlayEntity mapToEntity(UserTalePlay utp) {
        return new UserTalePlayEntity(
                utp.id,
                UserEntity.mapToEntity(utp.user),
                TaleEntity.mapToEntity(utp.tale),
                utp.playCount,
                utp.currentSceneId,
                utp.LastPlayedAt
        );
    }

}
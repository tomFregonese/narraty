package com.ynov.javaformation.narraty.data.entities;

import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.TaleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "tales")
@NoArgsConstructor
@AllArgsConstructor
public class TaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    public String title;

    public String description;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    @Column(name = "author_id")
    public UUID authorId;

    @OneToMany(mappedBy = "taleId", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    public Collection<SceneEntity> scenes;

    @Column(name = "play_count")
    public int playCount;

    @Enumerated(EnumType.STRING)
    public TaleStatus status;


    public Tale mapToDomain() {
        return new Tale(
                this.id,
                this.title,
                this.description,
                this.createdAt,
                this.updatedAt,
                this.authorId,
                this.scenes == null ?
                        null : this.scenes.stream()
                                .map(SceneEntity::mapToDomain)
                                .toList(),
                this.playCount,
                this.status
        );
    }

    public static TaleEntity mapToEntity(Tale tale) {
        return new TaleEntity(
                tale.id,
                tale.title,
                tale.description,
                tale.createdAt,
                tale.updatedAt,
                tale.authorId,
                tale.Scenes == null ?
                        null : tale.Scenes.stream()
                        .map(SceneEntity::mapToEntity)
                        .toList(),
                tale.playCount,
                tale.status
        );
    }

}

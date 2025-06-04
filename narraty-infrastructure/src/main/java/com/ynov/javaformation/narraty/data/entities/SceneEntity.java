package com.ynov.javaformation.narraty.data.entities;

import com.ynov.javaformation.narraty.models.Scene;
import com.ynov.javaformation.narraty.models.SceneStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "scenes")
@NoArgsConstructor
@AllArgsConstructor
public class SceneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    public String title;

    public String text;

    @Column(name = "tale_id")
    public UUID taleId;

    @Enumerated(EnumType.STRING)
    public SceneStatus status;

    @OneToMany(mappedBy = "sceneId", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    public Collection<ChoiceEntity> choices;


    public Scene mapToDomain() {
        return new Scene(
                this.id,
                this.title,
                this.text,
                this.taleId,
                this.status,
                this.choices == null ?
                        null : this.choices.stream()
                        .map(ChoiceEntity::mapToDomain)
                        .toList()
        );
    }

    public static SceneEntity mapToEntity(Scene scene) {
        return new SceneEntity(
                scene.id,
                scene.title,
                scene.text,
                scene.taleId,
                scene.status,
                scene.choices == null ? null :
                    scene.choices.stream()
                        .map(ChoiceEntity::mapToEntity)
                        .collect(Collectors.toList())
        );
    }

}

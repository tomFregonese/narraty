package com.ynov.javaformation.narraty.data.entities;

import com.ynov.javaformation.narraty.models.Choice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "choices")
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    public String text;

    @Column(name = "scene_id")
    public UUID sceneId;

    @Column(name = "next_scene_id")
    public UUID nextSceneId;


    public Choice mapToDomain() {
        return new Choice(
                this.id,
                this.text,
                this.sceneId,
                this.nextSceneId
        );
    }

    public static ChoiceEntity mapToEntity(Choice choice) {
        return new ChoiceEntity(
                choice.id,
                choice.text,
                choice.sceneId,
                choice.nextSceneId
        );
    }

}

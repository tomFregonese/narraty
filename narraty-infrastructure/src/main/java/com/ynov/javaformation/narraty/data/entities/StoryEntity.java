package com.ynov.javaformation.narraty.data.entities;

import com.ynov.javaformation.narraty.models.Story;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "stories")
@NoArgsConstructor
@AllArgsConstructor
public class StoryEntity {

    @Id
    @GeneratedValue
    public UUID id;

    @Column(nullable = false)
    public String title;

    @Column
    public String description;

    @Column(name = "created_at", nullable = false)
    public String createdAt;


    public Story mapToDomain() {
        return new Story(
                this.id,
                this.title,
                this.description,
                Date.from(Instant.parse(this.createdAt))
        );
    }

    public static StoryEntity mapToEntity(Story story) {
        return new StoryEntity (
                story.id,
                story.title,
                story.description,
                story.createdAt.toString()
        );
    }

}

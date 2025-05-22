package com.ynov.javaformation.narraty.models;

import lombok.AllArgsConstructor;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
public class Story {

    public UUID id;

    public String title;

    public String description;

    public Date createdAt;

}

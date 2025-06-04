package com.ynov.javaformation.narraty.dtos;

import com.ynov.javaformation.narraty.models.Tale;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaleDtoOut {

    String ttl;
    String dsc;
    LocalDateTime crtAt;

    public TaleDtoOut(Tale tale) {
        this.ttl = tale.title;
        this.dsc = tale.description;
        this.crtAt = tale.createdAt;
    }

}
package com.ynov.javaformation.narraty.dtosCore;

import com.ynov.javaformation.narraty.models.TaleStatus;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UpdateTaleStatusDtoCore {

    public UUID taleId;

    public TaleStatus status;

}

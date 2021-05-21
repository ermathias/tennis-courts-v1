package com.tenniscourts.tenniscourts;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateTennisCourtRequestDTO {
    @NotNull
    private String name;
}

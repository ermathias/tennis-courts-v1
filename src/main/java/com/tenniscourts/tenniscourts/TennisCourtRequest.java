package com.tenniscourts.tenniscourts;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TennisCourtRequest {

    @NotNull
    private String name;
}

package com.tenniscourts.guests;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GuestDTO {

    private Long id;

    @NotNull
    private String name;

    @ApiModelProperty(value = "Boolean used for role identification. true = Tennis Court Admin, false = normal user")
    @NotNull
    private boolean admin;
}

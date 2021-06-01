package com.tenniscourts.guests;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestDTO {

    @ApiModelProperty(name = "id")
    private Long id;

    @ApiModelProperty(name = "name", required = true, value = "Roger")
    @NotNull(message = "Name cannot be null")
    private String name;

}

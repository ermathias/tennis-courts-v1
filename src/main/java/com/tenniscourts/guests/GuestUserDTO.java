package com.tenniscourts.guests;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestUserDTO {

    private Long id;

    @NotNull
    private String name;

}

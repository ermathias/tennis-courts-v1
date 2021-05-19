package com.tenniscourts.guests;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestDTO {

    @NotNull
    private Long id;

    @NotNull
    private String name;
}

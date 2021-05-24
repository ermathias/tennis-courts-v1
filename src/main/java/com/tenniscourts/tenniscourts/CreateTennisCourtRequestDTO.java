package com.tenniscourts.tenniscourts;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTennisCourtRequestDTO {

    private Long id;

    @NotNull
    private String name;
}

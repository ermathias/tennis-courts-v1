package com.tenniscourts.tenniscourts;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class CreateTennisCourtDTO {

    @NotNull
    private String name;
}

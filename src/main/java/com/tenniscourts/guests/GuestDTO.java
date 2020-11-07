package com.tenniscourts.guests;

import lombok.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GuestDTO {

    private Long id;

    @NotNull
    private String name;
}

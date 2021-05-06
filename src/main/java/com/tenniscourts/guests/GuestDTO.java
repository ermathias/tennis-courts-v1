package com.tenniscourts.guests;

import javax.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GuestDTO {

    private Long id;

    @NotNull
    private String name;
}
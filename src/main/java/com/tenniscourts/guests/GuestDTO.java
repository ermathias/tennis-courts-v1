package com.tenniscourts.guests;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestDTO {
    private Long id;

    @NotNull
    private String name;

    private BigDecimal balance;
}

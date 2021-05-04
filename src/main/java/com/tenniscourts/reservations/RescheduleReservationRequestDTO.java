package com.tenniscourts.reservations;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class RescheduleReservationRequestDTO {

    @NotNull
    private Long reservationId;

    @NotNull
    private Long newScheduleId;

    @NotNull
    private BigDecimal value;

}


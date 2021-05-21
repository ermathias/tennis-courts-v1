package com.tenniscourts.reservations;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReservationRescheduleRequestDTO {
    @NotNull
    private Long scheduleId;
}

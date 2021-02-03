package com.tenniscourts.reservations;

import com.tenniscourts.guests.GuestDTO;
import com.tenniscourts.schedules.ScheduleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class ReservationDTO {

    private Long id;

    private ScheduleDTO schedule;

    private GuestDTO guest;

    private String reservationStatus;

    private ReservationDTO previousReservation;

    private BigDecimal refundValue;

    private BigDecimal value;

    @NotNull
    private Long scheduledId;

    @NotNull
    private Long guestId;

    @NotNull
    private LocalDateTime startDateTime;

    @NotNull
    private LocalDateTime endDateTime;

}

package com.tenniscourts.reservations;

import com.tenniscourts.schedules.ScheduleDTO;
import io.swagger.annotations.ApiModelProperty;
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
public class ReservationDTO {

    @ApiModelProperty(name = "id")
    private Long id;

    private ScheduleDTO schedule;

    @ApiModelProperty(name = "reservationStatus", value = "READY_TO_PLAY")
    private String reservationStatus;

    private ReservationDTO previousReservation;

    @ApiModelProperty(name = "refundValue", value = "10")
    private BigDecimal refundValue;

    @ApiModelProperty(name = "value", value = "20", notes = "Reservation fee")
    private BigDecimal value;

    @ApiModelProperty(name = "scheduledId", required = true)
    @NotNull
    private Long scheduledId;

    @ApiModelProperty(name = "guestId", required = true)
    @NotNull
    private Long guestId;
}

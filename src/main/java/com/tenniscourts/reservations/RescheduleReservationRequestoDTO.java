package com.tenniscourts.reservations;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RescheduleReservationRequestoDTO {
	
	@NotNull
	private Long reservationId;
	
	@NotNull
	private Long scheduleId;

}

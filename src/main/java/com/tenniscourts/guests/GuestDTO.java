package com.tenniscourts.guests;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tenniscourts.reservations.ReservationDTO;
import com.tenniscourts.reservations.ReservationDTO.ReservationDTOBuilder;
import com.tenniscourts.schedules.ScheduleDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class GuestDTO {

	@NotNull
	private Long tennisCourtId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull
	private String startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull
	private String endDate;

}

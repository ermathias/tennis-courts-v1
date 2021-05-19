package com.tenniscourts.guests;

import javax.validation.constraints.NotNull;

import com.tenniscourts.reservations.CreateReservationRequestDTO;
import com.tenniscourts.reservations.CreateReservationRequestDTO.CreateReservationRequestDTOBuilder;

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
public class CreateGuestRequestDTO {
	 @NotNull
	 private Long guestId;
}

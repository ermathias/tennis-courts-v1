package com.tenniscourts.guests.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGuestRequestDTO {
	
	@NotNull
	private Long id;
	
	@NotBlank
	private String name;

}

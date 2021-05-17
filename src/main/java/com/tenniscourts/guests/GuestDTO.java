package com.tenniscourts.guests;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestDTO {

	private Long id;
	@NotBlank(message="Name should not be empty")
	private String name;
}

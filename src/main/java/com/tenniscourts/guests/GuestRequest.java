package com.tenniscourts.guests;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestRequest {

	@NotBlank(message = "Name should not empty")
	private String name;
}

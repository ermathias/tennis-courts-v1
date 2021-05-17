package com.tenniscourts.tenniscourts;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TennisCourtRequest {

	@NotBlank(message = "Name should not empty")
	private String name;
}

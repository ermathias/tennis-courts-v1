package com.tenniscourts.guests.to;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public final class GuestCreateDTO
	implements
	Serializable {

	@NotEmpty
	private String name;
}

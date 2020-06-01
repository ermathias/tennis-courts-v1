package com.tenniscourts.guests.to;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public final class GuestDTO
	implements
	Serializable {

	private Long id;
	private String name;
}

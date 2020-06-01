package com.tenniscourts.guests.to;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public final class GuestUpdateDTO
	implements
	Serializable {

	private String name;
}

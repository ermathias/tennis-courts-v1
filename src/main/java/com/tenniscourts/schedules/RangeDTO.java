package com.tenniscourts.schedules;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RangeDTO {

	
	private LocalDate startDate;

	
	private LocalDate endDate;

}

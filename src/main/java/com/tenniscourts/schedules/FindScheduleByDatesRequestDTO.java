package com.tenniscourts.schedules;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindScheduleByDatesRequestDTO {
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'")
    @NotNull
	private LocalDate startDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'")
    @NotNull
	private LocalDate endDate;

}

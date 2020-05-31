package com.tenniscourts.schedules;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateScheduleRequestBatchDTO {

	@NotEmpty
	private Collection<Long> tennisCourtId;
	@JsonFormat(
		pattern = "yyyy-MM-dd'T'HH:mm")
	@NotNull
	private LocalDateTime startDateTime;
}

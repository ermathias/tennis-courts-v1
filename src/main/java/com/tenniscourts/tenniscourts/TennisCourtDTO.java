package com.tenniscourts.tenniscourts;

import com.tenniscourts.schedules.ScheduleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class TennisCourtDTO {
	
	@Valid
	@NotNull
    private Long id;

	@Valid
    @NotNull
    private String name;

	@Valid
    private List<ScheduleDTO> tennisCourtSchedules;

}

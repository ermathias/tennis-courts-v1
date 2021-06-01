package com.tenniscourts.schedules;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleDTO {

    @ApiModelProperty(name = "id")
    private Long id;

    private TennisCourtDTO tennisCourt;

    @ApiModelProperty(name = "tennisCourtId", required = true)
    @NotNull
    private Long tennisCourtId;

    @ApiModelProperty(name = "startDateTime", required = true, value = "2020-11-22T10:00")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    @NotNull
    private LocalDateTime startDateTime;

    @ApiModelProperty(name = "endDateTime", value = "2020-11-22T10:00")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDateTime;

}

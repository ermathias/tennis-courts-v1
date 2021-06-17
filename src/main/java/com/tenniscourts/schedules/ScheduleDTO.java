package com.tenniscourts.schedules;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleDTO {

    private Long id;

    private TennisCourtDTO tennisCourt;

    @NotNull
    private Long tennisCourtId;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    @NotNull
    private LocalDateTime startDateTime;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleDTO that = (ScheduleDTO) o;

        if (!tennisCourtId.equals(that.tennisCourtId)) return false;
        return startDateTime.equals(that.startDateTime);
    }

    @Override
    public int hashCode() {
        int result = tennisCourtId.hashCode();
        result = 31 * result + startDateTime.hashCode();
        return result;
    }
}

package com.tenniscourts.mapper;

import com.tenniscourts.dto.ScheduleDTO;
import com.tenniscourts.model.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleMapper {

    Schedule map(ScheduleDTO source);

    ScheduleDTO map(Schedule source);

    List<ScheduleDTO> map(List<Schedule> source);
}

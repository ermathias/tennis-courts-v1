package com.tenniscourts.schedules;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = ScheduleService.class)
public interface ScheduleMapper {

    Schedule map(ScheduleDTO source);

    ScheduleDTO map(Schedule source);

    List<ScheduleDTO> map(List<Schedule> source);
}

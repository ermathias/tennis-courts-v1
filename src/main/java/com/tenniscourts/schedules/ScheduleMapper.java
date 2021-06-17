package com.tenniscourts.schedules;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    Schedule map(ScheduleDTO source);

    @Mapping(target = "tennisCourtId", source = "tennisCourt.id")
    ScheduleDTO map(Schedule source);

    List<ScheduleDTO> map(List<Schedule> source);
}

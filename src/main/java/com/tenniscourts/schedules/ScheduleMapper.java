package com.tenniscourts.schedules;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

	@Mapping(target = "tennisCourt.id", source = "tennisCourtId")
    Schedule map(ScheduleDTO source);

    @InheritInverseConfiguration
    ScheduleDTO map(Schedule source);

    List<ScheduleDTO> map(List<Schedule> source);
}

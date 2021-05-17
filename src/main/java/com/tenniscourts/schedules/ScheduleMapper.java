package com.tenniscourts.schedules;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

	public static final ScheduleMapper SCHEDULE_MAPPER_INSTANCE = Mappers.getMapper(ScheduleMapper.class);

	Schedule map(ScheduleDTO source);

	ScheduleDTO map(Schedule source);

	List<ScheduleDTO> map(List<Schedule> source);

	Schedule map(CreateScheduleRequestDTO createScheduleRequestDTO);

}

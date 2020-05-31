package com.tenniscourts.schedules;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tenniscourts.tenniscourts.TennisCourtMapper;


@Component
public class ScheduleMapperDefault
	implements
	ScheduleMapper {

	private final TennisCourtMapper mapper;

	@Autowired
	public ScheduleMapperDefault(
		final TennisCourtMapper mapper) {
		super();
		this.mapper = mapper;
	}

	@Override
	public Schedule map(
		final ScheduleDTO source) {
		return Schedule.builder()
				.startDateTime(source.getStartDateTime())
				.endDateTime(source.getStartDateTime().plusHours(1L))
				.tennisCourt(mapper.map(source.getTennisCourt()))
				.build();
	}

	@Override
	public ScheduleDTO map(
		final Schedule source) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public List<ScheduleDTO> map(
		final List<Schedule> source) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}

package com.tenniscourts.schedules;

import java.util.List;

import org.springframework.stereotype.Component;


@Component
public class ScheduleMapperDefault
	implements
	ScheduleMapper {

	@Override
	public Schedule map(
		final ScheduleDTO source) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
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

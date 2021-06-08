package com.tenniscourts.schedules;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import com.tenniscourts.utils.Constants;

public class ScheduleMapperImpl implements ScheduleMapper {

	@Override
	public Schedule map(ScheduleDTO source) {
		return null;
	}

	@Override
	public ScheduleDTO map(Schedule source) {
		TennisCourt tennisCourt = source.getTennisCourt();
		TennisCourtDTO tennisCourtDTO = new TennisCourtDTO(tennisCourt.getId(), tennisCourt.getName(), new ArrayList<>());
		ScheduleDTO scheduleDTO = new ScheduleDTO(source.getId(), tennisCourtDTO, tennisCourtDTO.getId(), source.getStartDateTime(),
				source.getEndDateTime());
		return scheduleDTO;
	}

	@Override
	public List<ScheduleDTO> map(List<Schedule> source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Schedule map(CreateScheduleRequestDTO source) {
		TennisCourt tennisCourt = new TennisCourt();
		tennisCourt.setId(source.getTennisCourtId());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN);
    	LocalDateTime startDateTime = LocalDateTime.parse(source.getStartDateTime(), formatter);
		Schedule schedule = new Schedule(tennisCourt, startDateTime, startDateTime.plusHours(1), new ArrayList<>());
		return schedule;
	}

}

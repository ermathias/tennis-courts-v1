package com.tenniscourts.schedules;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

	@Autowired
	private final ScheduleRepository scheduleRepository;

	@Autowired
	private final ScheduleMapper scheduleMapper;

	public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
		// TODO: implement addSchedule
		return null;
	}

	public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
		// TODO: implement
		return null;
	}

	public ScheduleDTO findSchedule(Long scheduleId) {
		return scheduleMapper.map(scheduleRepository.findById(scheduleId).get());
	}

	public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
		return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
	}
}

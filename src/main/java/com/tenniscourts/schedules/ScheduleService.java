package com.tenniscourts.schedules;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.GuestDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleService {

	private final ScheduleRepository scheduleRepository;

	private final ModelMapper mapper = new ModelMapper();

	public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
		// TODO: implement addSchedule
		return null;
	}

	public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
		return scheduleRepository.findByStartDateTimeBetweenOrEndDateTimeBetween(startDate, endDate, startDate, endDate)
				.stream().map(schedule -> mapper.map(schedule, ScheduleDTO.class)).collect(Collectors.toList());
	}

	public ScheduleDTO findSchedule(Long scheduleId) {

		return scheduleRepository.findById(scheduleId).map(schedule -> {

			return mapper.map(schedule, ScheduleDTO.class);

		}).orElseThrow(() -> {
			throw new EntityNotFoundException("Schedule not found.");
		});
		
	}

	public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
		return null; // scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
	}
}

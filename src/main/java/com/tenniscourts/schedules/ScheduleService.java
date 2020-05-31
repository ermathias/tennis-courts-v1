package com.tenniscourts.schedules;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    private final TennisCourtScheduleService service;

	public ScheduleDTO addSchedule(
		final Long tennisCourtId,
		final CreateScheduleRequestDTO createScheduleRequestDTO) {
		LocalDateTime startDateTime = createScheduleRequestDTO.getStartDateTime();
		ScheduleDTO scheduleDTO = new ScheduleDTO();
		scheduleDTO.setStartDateTime(startDateTime);
		scheduleDTO.setEndDateTime(startDateTime.plusHours(1L));
		scheduleDTO.setTennisCourtId(tennisCourtId);
		scheduleDTO.setTennisCourt(service.findTennisCourt(tennisCourtId));
		Schedule schedule = save(scheduleDTO);
		scheduleDTO.setId(schedule.getId());
		return scheduleDTO;
	}

	private Schedule save(
		final ScheduleDTO scheduleDTO) {
		Schedule schedule = scheduleMapper.map(scheduleDTO);
		return scheduleRepository.save(schedule);
	}

    public List<ScheduleDTO> findSchedulesByDates(final LocalDateTime startDate, final LocalDateTime endDate) {
        //TODO: implement
        return null;
    }

    public ScheduleDTO findSchedule(final Long scheduleId) {
        //TODO: implement
        return null;
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(final Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}

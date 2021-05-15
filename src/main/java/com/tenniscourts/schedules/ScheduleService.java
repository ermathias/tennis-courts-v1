package com.tenniscourts.schedules;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.AlreadyExistsEntityException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.exceptions.InvalidDateTimeException;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleService {

	private final ScheduleRepository scheduleRepository;
	private final TennisCourtRepository tennisCourtRepository;

	public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
		if (createScheduleRequestDTO.getStartDateTime().isBefore(LocalDateTime.now())) {
			throw new InvalidDateTimeException("Please Enter Future Date");
		}
		List<ScheduleDTO> schedules = findSchedulesByTennisCourtId(tennisCourtId);
		LocalDateTime endDate = createScheduleRequestDTO.getStartDateTime().plusHours(1L);

		schedules.forEach(schedule -> {
			checkExistance(schedule, createScheduleRequestDTO.getStartDateTime());
		});

		TennisCourt tennisCourt = tennisCourtRepository.findById(tennisCourtId)
				.orElseThrow(() -> new EntityNotFoundException("Tennis court not found"));
		Schedule schedule = ScheduleMapper.SCHEDULE_MAPPER_INSTANCE.map(createScheduleRequestDTO);
		schedule.setTennisCourt(tennisCourt);
		schedule.setEndDateTime(endDate);
		return ScheduleMapper.SCHEDULE_MAPPER_INSTANCE.map(scheduleRepository.saveAndFlush(schedule));

	}

	public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
		return ScheduleMapper.SCHEDULE_MAPPER_INSTANCE
				.map(scheduleRepository.findBystartDateTimeAndEndDateTime(startDate, endDate));
	}

	public ScheduleDTO findSchedule(Long scheduleId) throws EntityNotFoundException {
		return scheduleRepository.findById(scheduleId).map(ScheduleMapper.SCHEDULE_MAPPER_INSTANCE::map)
				.orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

	}

	public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
		return ScheduleMapper.SCHEDULE_MAPPER_INSTANCE
				.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
	}

	private Boolean checkExistance(ScheduleDTO scheduleDTO, LocalDateTime startDateTime) {
		if (startDateTime.isEqual(scheduleDTO.getStartDateTime())
				|| (startDateTime.isAfter(scheduleDTO.getStartDateTime())
						&& startDateTime.isBefore(scheduleDTO.getEndDateTime()))
				|| startDateTime.isEqual(scheduleDTO.getEndDateTime())) {
			throw new AlreadyExistsEntityException("This slot already scheduled");

		}
		return Boolean.TRUE;
	}

}

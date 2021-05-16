package com.tenniscourts.schedules;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.AlreadyExistsEntityException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.exceptions.InvalidDateTimeException;
import com.tenniscourts.tenniscourts.TennisCourtRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Sreeshma S Menon Schedule service
 *
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ScheduleService {

	private final ScheduleRepository scheduleRepository;
	private final TennisCourtRepository tennisCourtRepository;

	/**
	 * Method to insert schedule for given tennis court id
	 * 
	 * @param createScheduleRequestDTO
	 * @return {@link ScheduleDTO}
	 */
	public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
		log.info("Inserting schedule");
		if (createScheduleRequestDTO.getStartDateTime().isBefore(LocalDateTime.now())) {
			throw new InvalidDateTimeException("Please Enter Future Date");
		}
		findSchedulesByTennisCourtId(tennisCourtId).forEach(schedule -> {
			checkExistance(schedule, createScheduleRequestDTO.getStartDateTime());
		});

		return ScheduleMapper.SCHEDULE_MAPPER_INSTANCE.map(scheduleRepository.saveAndFlush(Schedule.builder()
				.tennisCourt(tennisCourtRepository.findById(tennisCourtId)
						.orElseThrow(() -> new EntityNotFoundException("Tennis court not found")))
				.startDateTime(createScheduleRequestDTO.getStartDateTime())
				.endDateTime(createScheduleRequestDTO.getStartDateTime().plusHours(1L)).build()));

	}

	/**
	 * Method to find schedules by dates
	 * 
	 * @param startDate
	 * @param endDate
	 * @return List of {@link ScheduleDTO}
	 */

	public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
		return ScheduleMapper.SCHEDULE_MAPPER_INSTANCE
				.map(scheduleRepository.findBystartDateTimeAndEndDateTime(startDate, endDate));
	}

	/**
	 * Method to find schedule by id
	 * 
	 * @param scheduleId
	 * @return {@link ScheduleDTO}
	 */
	public ScheduleDTO findSchedule(Long scheduleId) throws EntityNotFoundException {
		return scheduleRepository.findById(scheduleId).map(ScheduleMapper.SCHEDULE_MAPPER_INSTANCE::map)
				.orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

	}

	/**
	 * Method to find schedules by tennisCourtId
	 * 
	 * @param tennisCourtId
	 * @return List of {@link ScheduleDTO}
	 */
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

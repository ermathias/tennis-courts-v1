package com.tenniscourts.tenniscourts;

import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.ScheduleService;

import lombok.RequiredArgsConstructor;

/**
 * Tennis Court Service
 */

@Service
@RequiredArgsConstructor
public class TennisCourtService {

	private final TennisCourtRepository tennisCourtRepository;

	private final ScheduleService scheduleService;

	/**
	 * Method to Add Tennis Court
	 * 
	 * @param tennisCourtRequest
	 * @return {@link TennisCourtDTO}
	 */
	public TennisCourtDTO addTennisCourt(TennisCourtRequest tennisCourtRequest) {
		return TennisCourtMapper.TENNIS_COURT_MAPPER_INSTANCE.map(tennisCourtRepository
				.saveAndFlush(TennisCourtMapper.TENNIS_COURT_MAPPER_INSTANCE.map(tennisCourtRequest)));
	}

	/**
	 * Method to find Tennis Court By Id
	 * 
	 * @param id
	 * @return {@link TennisCourtDTO}
	 */
	public TennisCourtDTO findTennisCourtById(Long id) throws EntityNotFoundException {

		return tennisCourtRepository.findById(id).map(TennisCourtMapper.TENNIS_COURT_MAPPER_INSTANCE::map)
				.orElseThrow(() -> new EntityNotFoundException("Tennis Court not found."));

	}

	/**
	 * Method to find tennis court with schedule By Id
	 * 
	 * @param tennisCourtId
	 * @return {@link TennisCourtDTO}
	 */
	public TennisCourtDTO findTennisCourtWithSchedulesById(Long tennisCourtId) {

		TennisCourtDTO tennisCourtDTO = findTennisCourtById(tennisCourtId);
		tennisCourtDTO.setTennisCourtSchedules(scheduleService.findSchedulesByTennisCourtId(tennisCourtId));
		return tennisCourtDTO;
	}
}

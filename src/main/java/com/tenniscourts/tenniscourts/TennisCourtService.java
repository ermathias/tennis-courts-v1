package com.tenniscourts.tenniscourts;

import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.ScheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TennisCourtService {

	private final TennisCourtRepository tennisCourtRepository;

	private final ScheduleService scheduleService;

	public TennisCourtDTO addTennisCourt(TennisCourtRequest tennisCourtRequest) {
		return TennisCourtMapper.TENNIS_COURT_MAPPER_INSTANCE.map(tennisCourtRepository
				.saveAndFlush(TennisCourtMapper.TENNIS_COURT_MAPPER_INSTANCE.map(tennisCourtRequest)));
	}

	public TennisCourtDTO findTennisCourtById(Long id) throws EntityNotFoundException {

		return tennisCourtRepository.findById(id).map(TennisCourtMapper.TENNIS_COURT_MAPPER_INSTANCE::map)
				.orElseThrow(() -> new EntityNotFoundException("Tennis Court not found."));

	}

	public TennisCourtDTO findTennisCourtWithSchedulesById(Long tennisCourtId) {

		TennisCourtDTO tennisCourtDTO = findTennisCourtById(tennisCourtId);
		tennisCourtDTO.setTennisCourtSchedules(scheduleService.findSchedulesByTennisCourtId(tennisCourtId));
		return tennisCourtDTO;
	}
}

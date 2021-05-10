package com.tenniscourts.tenniscourts;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.ScheduleService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TennisCourtService {
	private final TennisCourtRepository tennisCourtRepository;
	private final ScheduleService scheduleService;
	private final TennisCourtMapper tennisCourtMapper;

	public TennisCourtDTO addTennisCourt(TennisCourtDTO tennisCourt) {
		return tennisCourtMapper.map(tennisCourtRepository.saveAndFlush(tennisCourtMapper.map(tennisCourt)));
	}

	public TennisCourtDTO findTennisCourtById(Long id) throws Exception{
		return tennisCourtRepository.findById(id).map(tennisCourtMapper::map).<RuntimeException>orElseThrow(() -> {
			throw new EntityNotFoundException("Tennis Court not found.");
		});
	}

	public TennisCourtDTO findTennisCourtWithSchedulesById(Long tennisCourtId) throws Exception {
		TennisCourtDTO tennisCourtDTO = findTennisCourtById(tennisCourtId);
		tennisCourtDTO.setTennisCourtSchedules(scheduleService.findSchedulesByTennisCourtId(tennisCourtId));
		return tennisCourtDTO;
	}
}

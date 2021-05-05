package com.tenniscourts.tenniscourts;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.ScheduleService;


import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TennisCourtService {

	@Autowired
	private final TennisCourtRepository tennisCourtRepository;

	private final ScheduleService scheduleService;

	@Autowired
	private final TennisCourtMapper tennisCourtMapper;

	public TennisCourtDTO addTennisCourt(TennisCourtDTO tennisCourt) {
		return tennisCourtMapper.map(tennisCourtRepository.saveAndFlush(tennisCourtMapper.map(tennisCourt)));
	}

	public TennisCourtDTO findTennisCourtById(Long id) {
		return tennisCourtRepository.findById(id).map(tennisCourtMapper::map).orElseThrow(() -> {
			throw new EntityNotFoundException("Tennis Court not found.");
		});
	}

	public TennisCourtDTO findTennisCourtByName(String name) {
		return (TennisCourtDTO) (tennisCourtRepository.findByname(name));

	}

	public TennisCourtDTO getallCourts() {
		return (TennisCourtDTO) (tennisCourtRepository.findAll());

	}
	
	public void deleteTennisCourtById(Long tennisCourtId) {
		 tennisCourtRepository.deleteById(tennisCourtId);
	}
	
	public void updatecourt(TennisCourtDTO tennisCourt) {

		tennisCourtRepository.save(tennisCourt);
	}

	public TennisCourtDTO findTennisCourtWithSchedulesById(Long tennisCourtId) {
		TennisCourtDTO tennisCourtDTO = findTennisCourtById(tennisCourtId);
		tennisCourtDTO.setTennisCourtSchedules(scheduleService.findSchedulesByTennisCourtId(tennisCourtId));
		return tennisCourtDTO;
	}
}

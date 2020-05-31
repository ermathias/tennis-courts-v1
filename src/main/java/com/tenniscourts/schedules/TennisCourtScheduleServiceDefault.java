package com.tenniscourts.schedules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import com.tenniscourts.tenniscourts.TennisCourtMapper;
import com.tenniscourts.tenniscourts.TennisCourtRepository;


@Component
public class TennisCourtScheduleServiceDefault
	implements
	TennisCourtScheduleService {

    private final TennisCourtRepository tennisCourtRepository;

    private final TennisCourtMapper tennisCourtMapper;

    @Autowired
	public TennisCourtScheduleServiceDefault(
		final TennisCourtRepository tennisCourtRepository,
		final TennisCourtMapper tennisCourtMapper) {
		super();
		this.tennisCourtRepository = tennisCourtRepository;
		this.tennisCourtMapper = tennisCourtMapper;
	}

	@Override
	public TennisCourtDTO findTennisCourt(
		final Long id) {
		return tennisCourtRepository.findById(id).map(tennisCourtMapper::map).orElseThrow(() -> {
			throw new EntityNotFoundException("Tennis Court not found.");
		});
	}
}


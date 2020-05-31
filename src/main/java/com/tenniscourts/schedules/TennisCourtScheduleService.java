package com.tenniscourts.schedules;

import com.tenniscourts.tenniscourts.TennisCourtDTO;

public interface TennisCourtScheduleService {

	TennisCourtDTO findTennisCourt(
		Long tennisCourtId);
}

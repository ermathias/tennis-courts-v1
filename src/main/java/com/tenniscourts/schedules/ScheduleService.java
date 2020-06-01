package com.tenniscourts.schedules;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import com.tenniscourts.tenniscourts.TennisCourtMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

	private final TennisCourtMapper tennisCourtMapper;

    private final TennisCourtScheduleService tennisCourtScheduleService;

	public ScheduleDTO addSchedule(
		final Long tennisCourtId,
		final CreateScheduleRequestDTO createScheduleRequestDTO) {
		TennisCourtDTO tennisCourtDTO = tennisCourtScheduleService.findTennisCourt(tennisCourtId);
		TennisCourt tennisCourt = tennisCourtMapper.map(tennisCourtDTO);

		LocalDateTime startDateTime = createScheduleRequestDTO.getStartDateTime();
		LocalDateTime endDateTime = startDateTime.plusHours(1L);

		Schedule scheduleUnsaved = new Schedule();
		scheduleUnsaved.setStartDateTime(startDateTime);
		scheduleUnsaved.setEndDateTime(endDateTime);
		scheduleUnsaved.setTennisCourt(tennisCourt);
		scheduleUnsaved.setEndDateTime(endDateTime);
		scheduleUnsaved.setTennisCourt(tennisCourt);
		Schedule scheduleSaved = scheduleRepository.save(scheduleUnsaved);
		return scheduleMapper.map(scheduleSaved);
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

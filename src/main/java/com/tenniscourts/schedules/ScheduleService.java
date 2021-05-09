package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TennisCourtRepository tennisCourtRepository;
    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        Validate.notNull(tennisCourtId, "Tennis court id can't be null");
        Validate.notNull(createScheduleRequestDTO.getStartDateTime(), "Start date time can't be null");

        TennisCourt tennisCourt = tennisCourtRepository.findById(tennisCourtId).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format("Could no find tennis court with id %s", tennisCourtId.toString()));
        });

        Schedule schedule = Schedule.builder()
                .startDateTime(createScheduleRequestDTO.getStartDateTime())
                .endDateTime(createScheduleRequestDTO.getStartDateTime().plusHours(1))
                .tennisCourt(tennisCourt)
                .build();

        return scheduleMapper.map(scheduleRepository.save(schedule));
    }

    public List<ScheduleDTO> findFreeSchedules(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findAllByTennisCourt_IdAndReservationsIsNull(tennisCourtId));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleMapper.map(scheduleRepository.findAllByStartDateTimeBetween(startDate, endDate));
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        Validate.notNull(scheduleId, "Schedule id can't be null");
        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format("Schedule not found with id %d", scheduleId));
        });
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}

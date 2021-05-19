package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private static final String TENNIS_COURT_NOT_FOUND = "Tennis Court not found in the system.";
    private static final String SCHEDULE_TENNIS_COURT_IN_THE_PAST = "User trying to schedule tennis court in the past";
    private static final String SCHEDULE_NOT_FOUND = "Schedule not found in the system.";


    @Autowired
    private final ScheduleRepository scheduleRepository;

    @Autowired
    private final ScheduleMapper scheduleMapper;

    @Autowired
    private final TennisCourtRepository tennisCourtRepository;


    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        TennisCourt tennisCourt = tennisCourtRepository.findById(tennisCourtId).orElseThrow(() -> {
            throw new EntityNotFoundException(TENNIS_COURT_NOT_FOUND);
        });

        if (LocalDateTime.now().isAfter(createScheduleRequestDTO.getStartDateTime())){
            throw new IllegalArgumentException(SCHEDULE_TENNIS_COURT_IN_THE_PAST);
        }

        //TODO: need to verify if there is already a schedule at this time, throw a already exist exception in case exists

        Schedule schedule = Schedule.builder()
                .startDateTime(createScheduleRequestDTO.getStartDateTime())
                .endDateTime(createScheduleRequestDTO.getStartDateTime().plusHours(1))
                .tennisCourt(tennisCourt)
                .build();

        return scheduleMapper.map(scheduleRepository.saveAndFlush(schedule));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleMapper.map(scheduleRepository.findByStartDateTimeBetween(startDate, endDate));
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException(SCHEDULE_NOT_FOUND);
        });
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}

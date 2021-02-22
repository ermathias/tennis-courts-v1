package com.tenniscourts.schedules;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;

@Service
@AllArgsConstructor
public class ScheduleService {

    @Inject
    private final ScheduleRepository scheduleRepository;

    @Inject
    private final TennisCourtRepository tennisCourtRepository;

    @Inject
    private final ScheduleMapper scheduleMapper;

    @Transactional
    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        TennisCourt savedTennisCourt = tennisCourtRepository.findById(tennisCourtId).orElse(null);
        Schedule newSchedule = null;

        if (savedTennisCourt == null) {
            throw new EntityNotFoundException("The informed tennis court was not found.");   
        }

        newSchedule = Schedule.builder()
            .tennisCourt(savedTennisCourt)
            .startDateTime(createScheduleRequestDTO.getStartDateTime())
            .endDateTime(createScheduleRequestDTO.getEndDateTime())
            .build();
        scheduleRepository.save(newSchedule);

        return scheduleMapper.map(newSchedule);
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        //TODO: implement
        return null;
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleMapper.map(scheduleRepository.findById(scheduleId).get());
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}

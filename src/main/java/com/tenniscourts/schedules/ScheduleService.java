package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    private final TennisCourtRepository tennisCourtRepository;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        TennisCourt tennisCourt = tennisCourtRepository.findById(tennisCourtId).orElseThrow(() -> new EntityNotFoundException("Tennis Court not found."));

        ScheduleDTO requestedSchedule = new ScheduleDTO();
        requestedSchedule.setTennisCourtId(tennisCourtId);
        requestedSchedule.setStartDateTime(createScheduleRequestDTO.getStartDateTime());

        List<ScheduleDTO> schedulesByTennisCourtId = findSchedulesByTennisCourtId(tennisCourtId);
        if (schedulesByTennisCourtId.stream().anyMatch(s -> s.equals(requestedSchedule))) {
            throw new IllegalArgumentException("Tennis Court is already scheduled for datetime");
        }

        Schedule newSchedule = new Schedule();
        newSchedule.setTennisCourt(tennisCourt);
        newSchedule.setStartDateTime(createScheduleRequestDTO.getStartDateTime());
        newSchedule.setEndDateTime(createScheduleRequestDTO.getStartDateTime().plus(1, ChronoUnit.HOURS));

        Schedule saved = scheduleRepository.save(newSchedule);
        return scheduleMapper.map(saved);
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        //TODO: implement
        return Collections.emptyList();
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).orElseThrow(() -> new EntityNotFoundException("Schedule not found."));
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}

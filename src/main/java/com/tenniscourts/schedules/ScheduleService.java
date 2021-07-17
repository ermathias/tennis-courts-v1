package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        final List<Schedule> schedules = scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId);
        final Schedule existingSchedule = schedules
                .stream()
                .findFirst()
                .<EntityNotFoundException>orElseThrow(() -> { throw new EntityNotFoundException("Schedules not found."); } );
        final Schedule newSchedule = new Schedule(
                existingSchedule.getTennisCourt(),
                createScheduleRequestDTO.getStartDateTime(),
                createScheduleRequestDTO.getStartDateTime().plusHours(1),
                new ArrayList<>()
        );
        scheduleRepository.saveAndFlush(newSchedule);
        return scheduleMapper.map(newSchedule);
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        final List<Schedule> schedules = scheduleRepository.findAll();
        final List<Schedule> filteredSchedules = schedules.stream().filter(o ->
            o.getStartDateTime().equals(startDate) && o.getEndDateTime().equals(endDate)
        ).collect(Collectors.toList());
        return scheduleMapper.map(filteredSchedules);
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        final Schedule schedule = scheduleRepository
                .findById(scheduleId)
                .<EntityNotFoundException>orElseThrow(() -> { throw new EntityNotFoundException("Schedules not found."); } );
        return scheduleMapper.map(schedule);
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}

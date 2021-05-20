package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final TennisCourtRepository tennisCourtRepository;

    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        //TODO: implement addSchedule
        TennisCourt tennisCourt = tennisCourtRepository.findById(tennisCourtId).orElseThrow(() -> {
            throw new EntityNotFoundException("Tennis court not found.");
        });

        Schedule schedule = new Schedule();
        schedule.setTennisCourt(tennisCourt);
        schedule.setStartDateTime(createScheduleRequestDTO.getStartDateTime());
        schedule.setEndDateTime(createScheduleRequestDTO.getStartDateTime().plusHours(1));

        return scheduleMapper.map(scheduleRepository.save(schedule));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        //TODO: implement
        return scheduleRepository.findAllByStartDateTimeBetween(startDate, endDate).stream().map(scheduleMapper::map).collect(Collectors.toList());
    }

    public List<ScheduleDTO> findFreeSchedules() {
        //TODO: implement
        return scheduleRepository.findAllByReservationsIsNull().stream().map(scheduleMapper::map).collect(Collectors.toList());
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        //TODO: implement
        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule not found.");
        });
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}

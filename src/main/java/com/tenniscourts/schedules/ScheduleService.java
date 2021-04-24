package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.AlreadyExistsEntityException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    private final TennisCourtRepository tennisCourtRepository;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {

        TennisCourt court = tennisCourtRepository.getOne(tennisCourtId);
        LocalDateTime endOfTime = LocalDateTime.of(createScheduleRequestDTO.getStartDateTime().toLocalDate(), createScheduleRequestDTO.getStartDateTime().toLocalTime()).plusHours(1);
        Schedule schedule = new Schedule(court, createScheduleRequestDTO.getStartDateTime(), endOfTime, null);
        if (Objects.isNull(scheduleRepository.findByStartDateTime(createScheduleRequestDTO.getStartDateTime()))) {
            return scheduleMapper.map(scheduleRepository.saveAndFlush(schedule));
        } else {
            throw new AlreadyExistsEntityException("This schedule already exist!!");
        }
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        var schedules = scheduleRepository.findByStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqual(startDate, endDate);
        return Optional.of(schedules).map(scheduleMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule not found.");
        });
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule not found.");
        });
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }

    public List<ScheduleDTO> findFreeSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findFreeSchedulesByTennisCourtId(tennisCourtId));
    }

}

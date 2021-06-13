package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourtMapper;
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

    private final ScheduleMapper scheduleMapper;

    private final TennisCourtMapper tennisCourtMapper;

    private final TennisCourtRepository tennisCourtRepository;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        if (createScheduleRequestDTO.getStartDateTime().isAfter(createScheduleRequestDTO.getEndDateTime())) {
            throw new IllegalStateException("The start date of the schedule should be before the end date.");
        }

        scheduleDTO.setTennisCourt(tennisCourtRepository
                .findById(createScheduleRequestDTO.getTennisCourtId())
                .map(tennisCourtMapper::map)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Tennis court with id %s not found",
                                createScheduleRequestDTO.getTennisCourtId()))));
        scheduleDTO.setStartDateTime(createScheduleRequestDTO.getStartDateTime());
        scheduleDTO.setEndDateTime(createScheduleRequestDTO.getEndDateTime());

        scheduleRepository.save(scheduleMapper.map(scheduleDTO));

        return scheduleDTO;
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        List<ScheduleDTO> allSchedules = scheduleRepository
                .findAll()
                .stream()
                .map(scheduleMapper::map)
                .collect(Collectors.toList());

        return allSchedules
                .stream()
                .filter(scheduleDTO ->
                        startDate.isBefore(scheduleDTO.getStartDateTime()) ||
                                startDate.isEqual(scheduleDTO.getStartDateTime()))
                .filter(scheduleDTO ->
                        scheduleDTO.getEndDateTime() != null &&
                                endDate.isAfter(scheduleDTO.getEndDateTime()) ||
                                endDate.isEqual(scheduleDTO.getEndDateTime()))
                .collect(Collectors.toList());
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleRepository
                .findById(scheduleId)
                .map(scheduleMapper::map)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Schedule with id %s not found", scheduleId)));
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}

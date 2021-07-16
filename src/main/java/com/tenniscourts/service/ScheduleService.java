package com.tenniscourts.service;

import com.tenniscourts.dto.CreateScheduleRequestDTO;
import com.tenniscourts.dto.ScheduleDTO;
import com.tenniscourts.exceptions.AlreadyExistsEntityException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.model.schedule.ScheduleMapper;
import com.tenniscourts.repository.ScheduleRepository;
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

    public ScheduleDTO addSchedule(CreateScheduleRequestDTO createScheduleRequestDTO) {
        List<ScheduleDTO> scheduleDTO = findSchedulesByTennisCourtId(createScheduleRequestDTO.getTennisCourtId());
        if (! scheduleDTO.stream().filter(f ->
                f.getStartDateTime().isEqual(createScheduleRequestDTO.getStartDateTime()) &&
                f.getEndDateTime().isEqual(createScheduleRequestDTO.getEndDateTime())).collect(Collectors.toList()).isEmpty()) {
            throw new AlreadyExistsEntityException("Cannot book same court with same date/time interval");
        }
        return scheduleMapper.map(scheduleRepository.save(scheduleMapper.map(createScheduleRequestDTO)));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleRepository.findSchedulesByStartDateTimeAndEndDateTime(startDate, endDate)
                .stream().map(scheduleMapper::map).collect(Collectors.toList());
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleMapper.map(scheduleRepository.findById(scheduleId).orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule not found.");
        }));
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourtIdOrderByStartDateTime(tennisCourtId));
    }

    public List<ScheduleDTO> findAll() {
        return scheduleRepository.findAll().stream().map(scheduleMapper::map).collect(Collectors.toList());
    }


}

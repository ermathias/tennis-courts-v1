package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourtRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.tenniscourts.reservations.ReservationStatus.getStatusUnavailableToScheduling;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    private final TennisCourtRepository tennisCourtRepository;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        return this.tennisCourtRepository.findById(tennisCourtId).map(tennisCourt -> {
            Schedule schedule = Schedule.builder()
                    .startDateTime(createScheduleRequestDTO.getStartDateTime())
                    .endDateTime(createScheduleRequestDTO.getStartDateTime().plusHours(1))
                    .tennisCourt(tennisCourt)
                    .build();
            return this.scheduleMapper.map(schedule);
        }).orElseThrow(() ->
            new EntityNotFoundException("Tennis Court not found.")
        );
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        return this.scheduleMapper.map(this.scheduleRepository.findAllByStartDateTimeBetweenOrderByStartDateTime(startDate, endDate));
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return this.scheduleRepository.findById(scheduleId).map(this.scheduleMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }

    public List<ScheduleDTO> getAvailableSchedules() {
        return this.scheduleRepository.findByStartDateGreaterThan_And_StatusDifferentFrom(LocalDateTime.now(),
                getStatusUnavailableToScheduling()).stream().map(scheduleMapper::map).collect(Collectors.toList());
    }
}

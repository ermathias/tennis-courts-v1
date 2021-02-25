package com.tenniscourts.schedules;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private final long DEFAULT_TIME_SLOT = 1L;

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
            .endDateTime(addDefaultTimeSlot(createScheduleRequestDTO.getStartDateTime()))
            .build();
        scheduleRepository.save(newSchedule);

        return scheduleMapper.map(newSchedule);
    }

    private LocalDateTime addDefaultTimeSlot(LocalDateTime startDateTime) {
        return startDateTime.plusHours(DEFAULT_TIME_SLOT);
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return scheduleMapper.map(scheduleRepository.findAllByStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqual(startDateTime, endDateTime));
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        Schedule savedSchedule = scheduleRepository.findById(scheduleId).orElse(null);

        if (savedSchedule == null) {
            throw new EntityNotFoundException("The informed schedule was not found.");   
        }

        return scheduleMapper.map(scheduleRepository.findById(scheduleId).get());
    }

    public List<ScheduleDTO> findSchedulesByTennisCourt(TennisCourt tennisCourt) {
        return scheduleMapper.map(scheduleRepository.findAllByTennisCourt(tennisCourt));
    }

    public List<ScheduleDTO> findSchedulesWithFreeTimeSlotsByScheduleDate(LocalDate scheduleDate) {
        List<Schedule> schedulesWithReservationsDifferentThanReadyToPlay = 
            scheduleRepository.findSchedulesWithReservationsDifferentThanReadyToPlayByScheduleDate(scheduleDate);

        List<Schedule> schedulesWithNoReservations = scheduleRepository.findSchedulesWithNoReservationsByScheduleDate(scheduleDate);

        schedulesWithReservationsDifferentThanReadyToPlay.addAll(schedulesWithNoReservations); // JPA/Hybernate doesn't support UNION operator

        return scheduleMapper.map(schedulesWithReservationsDifferentThanReadyToPlay);
    }

    public Schedule findById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElse(null);
    }
}

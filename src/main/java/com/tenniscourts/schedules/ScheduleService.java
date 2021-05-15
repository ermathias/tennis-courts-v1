package com.tenniscourts.schedules;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.AlreadyExistsEntityException;
import com.tenniscourts.reservations.ReservationRepository;
import com.tenniscourts.reservations.ReservationStatus;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {
	@Autowired
    private final ScheduleRepository scheduleRepository;
	@Autowired
    private final ScheduleMapper scheduleMapper;
	
    @Autowired
    private final TennisCourtRepository tennisCourtRepository;

    @Autowired
    private final ReservationRepository reservationRepository;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        if(createScheduleRequestDTO.getStartDateTime().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Can create schedule only for future dates");
        }

        TennisCourt tennisCourt = tennisCourtRepository.findById(tennisCourtId).orElseThrow(() -> {
            throw new IllegalArgumentException("Tennis Court not Found");
        });

        if(scheduleRepository.findByStartDateTimeBetweenAndTennisCourt(createScheduleRequestDTO.getStartDateTime().minusHours(1), createScheduleRequestDTO.getStartDateTime(), tennisCourt) != null){
            throw new AlreadyExistsEntityException("The Schedule of this Tennis Court at the given time already exists");
        }

    	Schedule schedule = Schedule.builder()
                .tennisCourt(tennisCourt)
                .startDateTime(createScheduleRequestDTO.getStartDateTime())
                .endDateTime(createScheduleRequestDTO.getStartDateTime().plusHours(1L))
                .build();

        return scheduleMapper.map(scheduleRepository.saveAndFlush(schedule));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleMapper.map(scheduleRepository.findByStartDateTimeBetween(startDate, endDate));
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        
        return scheduleMapper.map(scheduleRepository.findById(scheduleId).get());
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }

    public List<ScheduleDTO> findFreeSlots() {
        List<Long> ids =  new ArrayList<>();
        ids.add(0L);
        List<ReservationStatus> freeStatus = List.of(ReservationStatus.CANCELLED, ReservationStatus.RESCHEDULED);
        reservationRepository.findByReservationStatusNotIn(freeStatus).forEach(reservation -> ids.add(reservation.getSchedule().getId()));

        return scheduleMapper.map(scheduleRepository.findByIdNotInAndStartDateTimeGreaterThan(ids, LocalDateTime.now()));
    }
}

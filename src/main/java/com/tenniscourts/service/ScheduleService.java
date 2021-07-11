package com.tenniscourts.service;

import com.tenniscourts.dto.CreateScheduleRequestDTO;
import com.tenniscourts.dto.GuestDTO;
import com.tenniscourts.dto.ScheduleDTO;
import com.tenniscourts.dto.TennisCourtDTO;
import com.tenniscourts.exceptions.BusinessException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.mapper.ScheduleMapper;
import com.tenniscourts.model.*;
import com.tenniscourts.repository.ScheduleRepository;
import com.tenniscourts.util.Consts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.criteria.internal.BasicPathUsageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    private final @Value("${schedule.max.weeks}") int scheduleMaxWeeks;

    private final LocalDateTime currentDate;

    private final LocalDateTime endDateForScheduling;

    public ScheduleService(final ScheduleRepository scheduleRepository,
                           final ScheduleMapper scheduleMapper,
                           final @Value("${reservation.deposit}") int scheduleMaxWeeks){
        this.scheduleMapper =scheduleMapper;
        this.scheduleRepository = scheduleRepository;
        this.scheduleMaxWeeks = scheduleMaxWeeks;
        currentDate = LocalDateTime.now();
        endDateForScheduling = currentDate.plusWeeks(scheduleMaxWeeks);
    }

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        ScheduleDTO addedSchedule;

        List<Schedule> schedules =  scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId);
        List<ScheduleDTO> scheduleDTOList=  scheduleMapper.map(schedules);
        boolean tennisCourtIsFree = true;
        for (ScheduleDTO scheduleDTO : scheduleDTOList){
            if (scheduleDTO.getStartDateTime().isEqual(createScheduleRequestDTO.getStartDateTime())){
                tennisCourtIsFree = false;
            }
        }

        if (tennisCourtIsFree) {
            Schedule schedule = new Schedule();
            TennisCourt tennisCourt = new TennisCourt();
            tennisCourt.setId(tennisCourtId);
            schedule.setTennisCourt(tennisCourt);
            schedule.setStartDateTime(createScheduleRequestDTO.getStartDateTime());
            schedule.setEndDateTime(createScheduleRequestDTO.getStartDateTime().plusHours(Consts.ONE_HOUR));
            addedSchedule = scheduleMapper.map(scheduleRepository.save(schedule));
        } else {
            throw new BusinessException("Tennis Court has been already booked for this date");
        }


        return addedSchedule;

    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {

        return scheduleMapper.map(scheduleRepository.findSchedulesBetweenDates(startDate,endDate));

    }

    public List<ScheduleDTO> findFreeSlotsByDates(LocalDateTime startDate, LocalDateTime endDate, Long scheduleId) {
        List<Schedule> freeSchedules = new ArrayList<>();
        List<Schedule> schedules = scheduleRepository.findSchedulesBetweenDates(startDate,endDate);

        for (Schedule schedule : schedules){
            if (checkReservationIsNotReadyToPlay(schedule.getReservations())){
                if (scheduleId == null || scheduleId != schedule.getId()){
                    freeSchedules.add(schedule);
                }
            }
        }

        return scheduleMapper.map(freeSchedules);

    }
    public List<Reservation> getDailyReservation(LocalDateTime openingTime, LocalDateTime closingTime) {
        List<Reservation> dailyReservation = new ArrayList<>();
        List<Schedule> schedules = scheduleRepository.findSchedulesBetweenDates(openingTime, closingTime);
        for (Schedule schedule : schedules){
            dailyReservation.addAll(schedule.getReservations());
        }
        return dailyReservation;
    }

    private boolean checkReservationIsNotReadyToPlay(List<Reservation> reservations){
        boolean isReservationNotReadyToPlay = true;
        if (reservations != null && !reservations.isEmpty()) {
            for (Reservation reservation : reservations) {
                if (ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
                    isReservationNotReadyToPlay = false;
                }
            }
        }
        return isReservationNotReadyToPlay;
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule not found.");
        });
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }

    public List<ScheduleDTO> findFreeSlots() {

         return findFreeSlotsByDates(currentDate,endDateForScheduling, null);

    }

    public List<ScheduleDTO> findFreeSlots(Long currentScheduleId) {

        return findFreeSlotsByDates(currentDate,endDateForScheduling, currentScheduleId);

    }

    public void addReservationToSchedule(Long scheduleId, Reservation reservation) {
       Optional<Schedule> schedule = scheduleRepository.findById(scheduleId);
       schedule.get().addReservation(reservation);
       scheduleRepository.saveAndFlush(schedule.get());
    }

    public ScheduleDTO cancel(Long scheduleId) {
        Schedule schedule = scheduleRepository.getOne(scheduleId);
        List<Reservation> reservations = schedule.getReservations();
        if (!reservations.isEmpty()){
            throw new BusinessException
                    ("It is not allowed to cancel a Schedule linked to a reservation");
        }
        this.scheduleRepository.delete(schedule);
        return scheduleMapper.map(schedule);
    }

    public ScheduleDTO modify(ScheduleDTO scheduleDTO) {

        Optional<Schedule> schedule = scheduleRepository.findById(scheduleDTO.getId());
        ScheduleDTO result;

        if (schedule.isPresent()) {
            List<Reservation> reservations = schedule.get().getReservations();
            if (!reservations.isEmpty()){
              throw new BusinessException
                      ("It is not allowed to modify a Schedule linked to a reservation");
            }
            log.warn("A new schedule will be created with the new input.");
            CreateScheduleRequestDTO createScheduleRequestDTO = new CreateScheduleRequestDTO();
            createScheduleRequestDTO.setTennisCourtId(scheduleDTO.getTennisCourtId());
            createScheduleRequestDTO.setStartDateTime(scheduleDTO.getStartDateTime());
            log.warn("If the slot is already booked an exception will be thrown");
            ScheduleDTO newSchedule = addSchedule(scheduleDTO.getTennisCourtId(), createScheduleRequestDTO);
            log.warn("If the new slot has been created the old one will be deleted");
            cancel(scheduleDTO.getId());
            result = newSchedule;

        } else {
            throw new EntityNotFoundException("Schedule not found.");
        }
        return result;

    }


}

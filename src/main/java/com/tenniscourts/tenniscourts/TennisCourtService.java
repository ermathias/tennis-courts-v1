package com.tenniscourts.tenniscourts;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.reservations.Reservation;
import com.tenniscourts.reservations.ReservationService;
import com.tenniscourts.schedules.ScheduleDTO;
import com.tenniscourts.schedules.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TennisCourtService {

    private final TennisCourtRepository tennisCourtRepository;

    private final ScheduleService scheduleService;

    private final ReservationService reservationService;

    private final TennisCourtMapper tennisCourtMapper;

    private final GuestRepository guestRepository;

    public TennisCourtDTO addTennisCourt(Long userId, TennisCourtDTO tennisCourt) {
        Guest userAction = guestRepository.getOne(userId);
        if(userAction.isAdmin()){
            return tennisCourtMapper.map(tennisCourtRepository.saveAndFlush(tennisCourtMapper.map(tennisCourt)));
        }
        throw new UnsupportedOperationException("Only admin users are allowed to add tennis courts.");
    }

    public TennisCourtDTO findTennisCourtById(Long id) {
        return tennisCourtRepository.findById(id).map(tennisCourtMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Tennis Court not found.");
        });
    }

    public TennisCourtDTO findTennisCourtWithSchedulesById(Long tennisCourtId) {
        TennisCourtDTO tennisCourtDTO = findTennisCourtById(tennisCourtId);
        tennisCourtDTO.setTennisCourtSchedules(scheduleService.findSchedulesByTennisCourtId(tennisCourtId));
        return tennisCourtDTO;
    }

    public List<TennisCourtDTO> findAllCourtsWithFreeSchedules(){
        List<TennisCourt> courts = tennisCourtRepository.findAll();
        List<TennisCourtDTO> courtsDTO = new ArrayList<>();
        List<TennisCourtDTO> courtsWithFreeSchedules = new ArrayList<>();

        for(TennisCourt temp: courts){
            courtsDTO.add(convertToDto(temp));
        }

        for(TennisCourtDTO temp : courtsDTO){
            List<ScheduleDTO> freeSchedules = new ArrayList<>();
            List<ScheduleDTO> allSchedules = scheduleService.findSchedulesByTennisCourtId(temp.getId());
            for(ScheduleDTO schedule : allSchedules){
                List<Reservation> list = reservationService.findReservationByScheduleId(schedule.getId());
                if(list.isEmpty())
                    freeSchedules.add(schedule);
            }
            if(!freeSchedules.isEmpty()){
                temp.setTennisCourtSchedules(freeSchedules);
                courtsWithFreeSchedules.add(temp);
            }
        }
        return courtsWithFreeSchedules;
    }



    public List<TennisCourtDTO> findAllCourts(){
        List<TennisCourt> courts = tennisCourtRepository.findAll();
        List<TennisCourtDTO> courtsDTO = new ArrayList<>();

        for(TennisCourt temp: courts){
            courtsDTO.add(convertToDto(temp));
        }
        return courtsDTO;
    }


    //Converting Entity to DTO
    private TennisCourtDTO convertToDto(TennisCourt court){
        return tennisCourtMapper.map(court);
    }
}

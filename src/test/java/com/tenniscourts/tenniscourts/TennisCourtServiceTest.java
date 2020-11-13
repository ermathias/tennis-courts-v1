package com.tenniscourts.tenniscourts;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.reservations.Reservation;
import com.tenniscourts.reservations.ReservationService;
import com.tenniscourts.schedules.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = TennisCourtService.class)
public class TennisCourtServiceTest {

    @InjectMocks
    TennisCourtService tennisCourtService;
    @Mock
    ScheduleService scheduleService;
    @Mock
    ReservationService reservationService;
    @Mock
    TennisCourtRepository tennisCourtRepository;
    @Mock
    GuestRepository guestRepository;
    @Mock
    TennisCourtMapper tennisCourtMapper;
    @Mock
    ScheduleRepository scheduleRepository;
    @Mock
    ScheduleMapper scheduleMapper;


    @Test
    public void addTennisCourt(){
        Guest guest = new Guest();
        guest.setAdmin(true);
        guest.setName("Nadal");
        guest.setId(1L);

        TennisCourt court = new TennisCourt();
        court.setId(1L); court.setName("Qadra");

        TennisCourtDTO dto = new TennisCourtDTO();
        dto.setId(1L);
        dto.setName("Quadra");

        Mockito.when(guestRepository.getOne(1L)).thenReturn(guest);
        Mockito.when(tennisCourtMapper.map(dto)).thenReturn(court);
        Mockito.when(tennisCourtRepository.saveAndFlush(court)).thenReturn(court);
        Mockito.when(tennisCourtMapper.map(court)).thenReturn(dto);

        TennisCourtDTO answer = tennisCourtService.addTennisCourt(1L, dto);

        Mockito.verify(tennisCourtRepository).saveAndFlush(court);

        assertEquals(dto, answer);

    }

    @Test
    public void addTennisCourtNotAsAdmin(){
        Guest guest = new Guest();
        guest.setAdmin(false);
        guest.setName("Nadal");
        guest.setId(1L);

        Mockito.when(guestRepository.getOne(1L)).thenReturn(guest);

        try {
            tennisCourtService.addTennisCourt(1L, new TennisCourtDTO());
            fail("Should have thrown an exception");
        } catch (UnsupportedOperationException e){
            assertEquals("Only admin users are allowed to add tennis courts.", e.getMessage());
        }
    }

    @Test
    public void findTennisCourtById(){
        TennisCourt court = new TennisCourt();
        court.setId(1L);
        TennisCourtDTO dto = new TennisCourtDTO();
        dto.setId(1L);

        Mockito.when(tennisCourtRepository.findById(1L)).thenReturn(java.util.Optional.of(court));
        Mockito.when(tennisCourtMapper.map(court)).thenReturn(dto);

        TennisCourtDTO answer = tennisCourtService.findTennisCourtById(1L);

        assertEquals(dto, answer);

    }
    @Test
    public void findTennisCourtByIdNotFound(){
        TennisCourt court = new TennisCourt();
        court.setId(1L);

        try {
            tennisCourtService.findTennisCourtById(12323L);
            fail("Should have thrown an exception");
        } catch (EntityNotFoundException e){
            assertEquals("Tennis Court not found.", e.getMessage());
        }
    }

    @Test
    public void findTennisCourtWithSchedulesById(){
        TennisCourt court = new TennisCourt();
        court.setId(1L);

        Long id = 1L;
        Schedule schedule = new Schedule();
        schedule.setStartDateTime(LocalDateTime.now());
        Schedule schedule2 = new Schedule();
        schedule2.setStartDateTime(LocalDateTime.now());
        List<Schedule> listEnt = Arrays.asList(schedule, schedule2);

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setStartDateTime(LocalDateTime.now());
        ScheduleDTO scheduleDTO2 = new ScheduleDTO();
        scheduleDTO2.setStartDateTime(LocalDateTime.now());
        List<ScheduleDTO> listDto = Arrays.asList(scheduleDTO, scheduleDTO2);

        TennisCourtDTO courtDTO = new TennisCourtDTO();
        courtDTO.setId(1L);
        courtDTO.setTennisCourtSchedules(listDto);

        Mockito.when(scheduleService.findSchedulesByTennisCourtId(1L)).thenReturn(listDto);
        Mockito.when(tennisCourtRepository.findById(1L)).thenReturn(java.util.Optional.of(court));
        Mockito.when(tennisCourtMapper.map(court)).thenReturn(courtDTO);
        Mockito.when(tennisCourtService.findTennisCourtById(id)).thenReturn(courtDTO);

        TennisCourtDTO answer = tennisCourtService.findTennisCourtWithSchedulesById(id);

        assertEquals(courtDTO, answer);

    }

    @Test
    public void findAllCourts(){
        TennisCourt courtOne = new TennisCourt();
        courtOne.setId(1L);
        TennisCourt courtTwo = new TennisCourt();
        courtTwo.setId(2L);
        List<TennisCourt> tennisCourts = Arrays.asList(courtOne, courtTwo);

        TennisCourtDTO courtOneDto = new TennisCourtDTO();
        courtOneDto.setId(1L);
        TennisCourtDTO courtTwoDto = new TennisCourtDTO();
        courtTwoDto.setId(2L);
        List<TennisCourtDTO> list = new ArrayList<>();
        list.add(courtOneDto); list.add(courtTwoDto);

        Mockito.when(tennisCourtRepository.findAll()).thenReturn(tennisCourts);
        Mockito.when(tennisCourtMapper.map(courtOne)).thenReturn(courtOneDto);
        Mockito.when(tennisCourtMapper.map(courtTwo)).thenReturn(courtTwoDto);

        List<TennisCourtDTO> answer = tennisCourtService.findAllCourts();

       assertEquals(list, answer);
    }

    @Test
    public void findAllCourtsWithFreeSchedules(){
        Long id = 1L;

        TennisCourt courtOne = new TennisCourt();
        courtOne.setId(1L);
        List<TennisCourt> tennisCourts = Arrays.asList(courtOne);

        TennisCourtDTO courtOneDto = new TennisCourtDTO();
        courtOneDto.setId(1L);

        List<TennisCourtDTO> courtDTOS = new ArrayList<>();
        courtDTOS.add(courtOneDto);

        Schedule schedule = new Schedule();
        schedule.setStartDateTime(LocalDateTime.now());
        Schedule schedule2 = new Schedule();
        schedule2.setStartDateTime(LocalDateTime.now());
        List<Schedule> listEnt = Arrays.asList(schedule, schedule2);

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setStartDateTime(LocalDateTime.now());
        ScheduleDTO scheduleDTO2 = new ScheduleDTO();
        scheduleDTO2.setStartDateTime(LocalDateTime.now());
        List<ScheduleDTO> listDto = Arrays.asList(scheduleDTO, scheduleDTO2);

        Reservation one = new Reservation();
        one.setId(1L);
        Reservation two = new Reservation();
        two.setId(2L);
        List<Reservation> reservations = Arrays.asList(one, two);


        courtDTOS.get(0).setTennisCourtSchedules(listDto);

        Mockito.when(tennisCourtRepository.findAll()).thenReturn(tennisCourts);
        Mockito.when(tennisCourtMapper.map(courtOne)).thenReturn(courtOneDto);
        Mockito.when(scheduleService.findSchedulesByTennisCourtId(id)).thenReturn(listDto);

        List<TennisCourtDTO> answer = tennisCourtService.findAllCourtsWithFreeSchedules();

        assertEquals(courtDTOS, answer);
    }

}

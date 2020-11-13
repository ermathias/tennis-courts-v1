package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ScheduleService.class)
public class ScheduleServiceTest {

    @InjectMocks
    ScheduleService scheduleService;

    @Mock
    ScheduleRepository scheduleRepository;
    @Mock
    GuestRepository guestRepository;
    @Mock
    TennisCourtRepository tennisCourtRepository;

    @Mock
    ScheduleMapper scheduleMapper;

    @Test
    public void addScheduledNotAdminUser(){
        Guest guest = new Guest();
        guest.setAdmin(false);
        guest.setName("Nadal");
        guest.setId(1L);

        Mockito.when(guestRepository.getOne(1L)).thenReturn(guest);

        try {
            scheduleService.addSchedule(1L, 1L, new CreateScheduleRequestDTO());
            fail("Should have thrown an exception");
        } catch (UnsupportedOperationException e){
            assertEquals("Only admin users can create new schedules.", e.getMessage());
        }
    }

    @Test
    public void addScheduled(){
        Guest guest = new Guest();
        guest.setAdmin(true);
        guest.setName("Nadal");
        guest.setId(1L);

        TennisCourt court = new TennisCourt();
        court.setName("teste");
        court.setId(1L);

        CreateScheduleRequestDTO requestDTO = new CreateScheduleRequestDTO();
        requestDTO.setStartDateTime(LocalDateTime.now());

        LocalDateTime endOfTime = LocalDateTime.of(requestDTO.getStartDateTime().toLocalDate(), requestDTO.getStartDateTime().toLocalTime()).plusHours(1);
        Schedule schedule = new Schedule(court, requestDTO.getStartDateTime(), endOfTime, null);

        Mockito.when(guestRepository.getOne(1L)).thenReturn(guest);
        Mockito.when(tennisCourtRepository.getOne(1L)).thenReturn(court);

        scheduleService.addSchedule(1L, 1L, requestDTO);

        Mockito.verify(scheduleRepository).saveAndFlush(schedule);

    }

    @Test
    public void addScheduledAlreadyExists(){
        Guest guest = new Guest();
        guest.setAdmin(true);
        guest.setName("Nadal");
        guest.setId(1L);

        TennisCourt court = new TennisCourt();
        court.setName("teste");
        court.setId(1L);

        CreateScheduleRequestDTO requestDTO = new CreateScheduleRequestDTO();
        requestDTO.setStartDateTime(LocalDateTime.now());

        LocalDateTime endOfTime = LocalDateTime.of(requestDTO.getStartDateTime().toLocalDate(), requestDTO.getStartDateTime().toLocalTime()).plusHours(1);
        Schedule schedule = new Schedule(court, requestDTO.getStartDateTime(), endOfTime, null);

        Mockito.when(guestRepository.getOne(1L)).thenReturn(guest);
        Mockito.when(tennisCourtRepository.getOne(1L)).thenReturn(court);
        Mockito.when(scheduleRepository.findByStartDateTime(requestDTO.getStartDateTime())).thenReturn(schedule);

        try {
            scheduleService.addSchedule(1L, 1L, requestDTO);
            fail("Should have thrown an exception");
        } catch (UnsupportedOperationException e){
            assertEquals("This schedule already exist!!", e.getMessage());
        }

    }

    @Test
    public void findSchedulsByDates(){
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        Schedule schedule = new Schedule();
        schedule.setStartDateTime(start);
        List<Schedule> listEnt = Arrays.asList(schedule);

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setStartDateTime(start);
        scheduleDTO.setId(1L);
        List<ScheduleDTO> listDto = Arrays.asList(scheduleDTO);


        Mockito.when(scheduleRepository.StartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqual(start, end)).thenReturn(listEnt);
        Mockito.when(scheduleMapper.map(listEnt)).thenReturn(listDto);


        List<ScheduleDTO> answer = scheduleService.findSchedulesByDates(start, end);

        assertEquals(listDto, answer);

    }

    @Test
    public void findScheduleById(){
        Schedule schedule = new Schedule();
        schedule.setId(1L);
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(schedule.getId());

        Mockito.when(scheduleRepository.findById(1L)).thenReturn(java.util.Optional.of(schedule));
        Mockito.when(scheduleMapper.map(schedule)).thenReturn(dto);

        ScheduleDTO answer = scheduleService.findSchedule(1L);

        assertEquals(dto, answer);
    }

    @Test
    public void findScheduleByIdNotFound(){
        Schedule schedule = new Schedule();
        schedule.setId(1L);

        try {
           scheduleService.findSchedule(12323L);
            fail("Should have thrown an exception");
        } catch (EntityNotFoundException e){
            assertEquals("Schedule not found.", e.getMessage());
        }

    }

    @Test
    public void findSchedulesByTennisCourtId(){
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

        Mockito.when(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(id)).thenReturn(listEnt);
        Mockito.when(scheduleMapper.map(listEnt)).thenReturn(listDto);

        List<ScheduleDTO> answer = scheduleService.findSchedulesByTennisCourtId(id);

        assertEquals(listDto, answer);

    }
}

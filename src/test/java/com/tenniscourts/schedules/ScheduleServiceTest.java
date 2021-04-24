package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.AlreadyExistsEntityException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.FixtureTennisCourt;
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
import java.util.Optional;

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
    TennisCourtRepository tennisCourtRepository;

    @Mock
    ScheduleMapper scheduleMapper;

    @Test
    public void addScheduled(){
        var tennisCourt = FixtureTennisCourt.fixtureTennisCourt();
        var tennisCourtDTO = FixtureTennisCourt.fixtureTennisCourtDTO();
        var requestDTO = new CreateScheduleRequestDTO();
        requestDTO.setStartDateTime(LocalDateTime.now());

        LocalDateTime endOfTime = LocalDateTime.of(requestDTO.getStartDateTime().toLocalDate(), requestDTO.getStartDateTime().toLocalTime()).plusHours(1);
        Schedule schedule = new Schedule(tennisCourt, requestDTO.getStartDateTime(), endOfTime, null);
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setTennisCourt(tennisCourtDTO);
        scheduleDTO.setStartDateTime(requestDTO.getStartDateTime());
        scheduleDTO.setEndDateTime(endOfTime);

        Mockito.when(tennisCourtRepository.getOne(1L)).thenReturn(tennisCourt);
        Mockito.when(scheduleRepository.findByStartDateTime(requestDTO.getStartDateTime())).then(invocation -> null);

        scheduleService.addSchedule(1L, requestDTO);

        Mockito.verify(scheduleRepository).saveAndFlush(schedule);

    }

    @Test(expected = AlreadyExistsEntityException.class)
    public void addScheduledAlreadyExists(){

        var tennisCourt= FixtureTennisCourt.fixtureTennisCourt();

        CreateScheduleRequestDTO requestDTO = new CreateScheduleRequestDTO();
        requestDTO.setStartDateTime(LocalDateTime.now());

        LocalDateTime endOfTime = LocalDateTime.of(requestDTO.getStartDateTime().toLocalDate(), requestDTO.getStartDateTime().toLocalTime()).plusHours(1);
        Schedule schedule = new Schedule(tennisCourt, requestDTO.getStartDateTime(), endOfTime, null);

        Mockito.when(tennisCourtRepository.getOne(1L)).thenReturn(tennisCourt);
        Mockito.when(scheduleRepository.findByStartDateTime(requestDTO.getStartDateTime())).thenReturn(schedule);

        scheduleService.addSchedule(1L, requestDTO);
    }

    @Test
    public void findSchedulsByDates(){
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        var schedule = FixtureSchedules.fixtureSchedule(start);
        List<Schedule> listEnt = Arrays.asList(schedule);

        var scheduleDTO = FixtureSchedules.fixtureScheduleDTOWithID(start);
        List<ScheduleDTO> listDto = Arrays.asList(scheduleDTO);

        Mockito.when(scheduleRepository.findByStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqual(start, end)).thenReturn(listEnt);
        Mockito.when(scheduleMapper.map(listEnt)).thenReturn(listDto);

        List<ScheduleDTO> response = scheduleService.findSchedulesByDates(start, end);

        assertEquals(listDto, response);

    }

    @Test
    public void findScheduleById(){
        var schedule = FixtureSchedules.fixtureScheduleWithId();
        var scheduleDTO = FixtureSchedules.fixtureScheduleDTOWithID();

        Mockito.when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        Mockito.when(scheduleMapper.map(schedule)).thenReturn(scheduleDTO);

        ScheduleDTO response = scheduleService.findSchedule(1L);

        assertEquals(scheduleDTO, response);
    }

    @Test
    public void findScheduleByIdNotFound(){
        var schedule = FixtureSchedules.fixtureScheduleWithId();

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

        var firstSchedule = FixtureSchedules.fixtureSchedule();
        var secondSchedule = FixtureSchedules.fixtureSchedule();
        List<Schedule> listEnt = Arrays.asList(firstSchedule, secondSchedule);

        var firstScheduleDTO = FixtureSchedules.fixtureScheduleDTO();
        var secondScheduleDTO = FixtureSchedules.fixtureScheduleDTO();
        List<ScheduleDTO> listDto = Arrays.asList(firstScheduleDTO, secondScheduleDTO);

        Mockito.when(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(id)).thenReturn(listEnt);
        Mockito.when(scheduleMapper.map(listEnt)).thenReturn(listDto);

        List<ScheduleDTO> response = scheduleService.findSchedulesByTennisCourtId(id);

        assertEquals(listDto, response);

    }
}
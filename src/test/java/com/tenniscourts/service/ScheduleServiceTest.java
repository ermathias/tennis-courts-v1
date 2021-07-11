package com.tenniscourts.service;

import com.tenniscourts.dto.CreateScheduleRequestDTO;
import com.tenniscourts.dto.ScheduleDTO;
import com.tenniscourts.dto.TennisCourtDTO;
import com.tenniscourts.mapper.ScheduleMapper;
import com.tenniscourts.model.Schedule;
import com.tenniscourts.model.TennisCourt;
import com.tenniscourts.repository.ScheduleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ScheduleService.class)
public class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ScheduleMapper scheduleMapper;


    private ScheduleService scheduleService;



    @Before
    public void setup(){
        scheduleService = new ScheduleService(
                scheduleRepository, scheduleMapper, 6);
    }

    @Test
    public void addScheduleTest() {

        List<Schedule> schedules = new ArrayList<>();
        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setStartDateTime(LocalDateTime.now().plusDays(5));
        schedule.setEndDateTime(LocalDateTime.now().plusDays(5).plusHours(1));
        TennisCourt tennisCourt = new TennisCourt();
        tennisCourt.setId(1L);
        tennisCourt.setName("Wimbledon");
        schedule.setTennisCourt(tennisCourt);
        when(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(any())).thenReturn(schedules);


        List<ScheduleDTO> scheduleDTOList=  new ArrayList<>();
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(1L);
        TennisCourtDTO tennisCourtDto = new TennisCourtDTO();
        tennisCourt.setId(1L);
        tennisCourt.setName("Wimbledon");
        scheduleDTO.setTennisCourt(tennisCourtDto);
        scheduleDTO.setStartDateTime(LocalDateTime.now().plusDays(5));
        scheduleDTO.setEndDateTime(LocalDateTime.now().plusDays(5).plusHours(1));

        when(scheduleMapper.map(Mockito.anyList())).thenReturn(scheduleDTOList);

        CreateScheduleRequestDTO createScheduleRequestDTO = new CreateScheduleRequestDTO();

        createScheduleRequestDTO.setTennisCourtId(1L);
        createScheduleRequestDTO.setStartDateTime( LocalDateTime.now().plusDays(5).plusHours(2));

        ScheduleDTO scheduleDTOSaved = new ScheduleDTO();
        scheduleDTO.setId(2L);
        Schedule scheduleSaved = new Schedule();
        scheduleSaved.setId(2L);
        when(scheduleRepository.save(any())).thenReturn(scheduleSaved);
        when(scheduleMapper.map(Mockito.any(Schedule.class))).thenReturn(scheduleDTOSaved);

        ScheduleDTO scheduleDTOResult = scheduleService.addSchedule(1L,createScheduleRequestDTO);

        Assert.assertEquals(scheduleDTOResult.getId(), Long.getLong("2L"));
    }

    @Test
    public void findScheduleByID() {


        Optional<Schedule> schedule1 = Optional.of( new Schedule());
        schedule1.get().setId(1L);
        ScheduleDTO scheduleDTO1 = new ScheduleDTO();
        scheduleDTO1.setId(1L);
        when(scheduleRepository.findById(any())).thenReturn(schedule1);
        when(scheduleMapper.map(any(Schedule.class))).thenReturn(scheduleDTO1);
        ScheduleDTO scheduleDTOsResult = scheduleService.
                findSchedule(1L);

    }

    @Test
    public void findScheduleBetweenDatesTest() {
        List<Schedule> schedules = new ArrayList<>();
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();

        Schedule schedule1 = new Schedule();
        schedule1.setId(1L);
        Schedule schedule2 = new Schedule();
        schedule2.setId(2L);
        schedules.add(schedule1);
        schedules.add(schedule2);
        ScheduleDTO scheduleDTO1 = new ScheduleDTO();
        scheduleDTO1.setId(1L);
        ScheduleDTO scheduleDTO2 = new ScheduleDTO();
        scheduleDTO2.setId(2L);
        scheduleDTOs.add(scheduleDTO1);
        scheduleDTOs.add(scheduleDTO2);
        when(scheduleRepository.findSchedulesBetweenDates(any(),any())).thenReturn(schedules);
        when(scheduleMapper.map(anyList())).thenReturn(scheduleDTOs);
        List<ScheduleDTO> scheduleDTOsResult = scheduleService.
                findSchedulesByDates(LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(2).plusHours(6));

        Assert.assertEquals(scheduleDTOsResult.size(), 2);
    }



}

package com.tenniscourts.service;

import com.tenniscourts.Fixtures;
import com.tenniscourts.storage.CreateScheduleRequestDTO;
import com.tenniscourts.storage.ScheduleDTO;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.junit.Test;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = GuestService.class)
@SpringBootTest
public class ScheduleServiceTest {

    @Mock
    private ScheduleService scheduleService;

    @Test
    @DisplayName("Test verify the method of service ScheduleService - AddSchedule -")
    public void verifyAddScheduleTest() {
        LocalDateTime startDateTime = LocalDateTime.of(2021, 2, 13, 15, 56);
        LocalDateTime endDateTime = LocalDateTime.of(2021, 2, 13, 16, 56);
        ScheduleDTO scheduleDTO = Fixtures.buildScheduleDTO();
        given(scheduleService.addSchedule(1L, Fixtures.buildCreateScheduleRequestDTO())).willReturn(scheduleDTO);

        CreateScheduleRequestDTO createScheduleRequestDTO = CreateScheduleRequestDTO.builder()
                .startDateTime(startDateTime)
                .startDateTime(endDateTime)
                .tennisCourtId(1L)
                .build();
        ScheduleDTO scheduleDTO1 = scheduleService.addSchedule(1L, createScheduleRequestDTO);
        Assert.assertEquals(scheduleDTO.getStartDateTime(),scheduleDTO1.getStartDateTime());
    }

    @Test
    @DisplayName("Test verify the method of service ScheduleService - FindSchedule -")
    public void verifyFindSchedule() {
        LocalDateTime startDateTime = LocalDateTime.of(2021, 2, 13, 15, 56);
        LocalDateTime endDateTime = LocalDateTime.of(2021, 2, 13, 16, 56);
        ScheduleDTO scheduleDTO = Fixtures.buildScheduleDTO();
        given(scheduleService.findSchedulesByDates(startDateTime, endDateTime)).willReturn(List.of(scheduleDTO));

        List<ScheduleDTO> listScheduleDTO =  scheduleService.findSchedulesByDates(startDateTime, endDateTime);
        Assert.assertEquals(listScheduleDTO.get(0).getTennisCourt(), scheduleDTO.getTennisCourt());
        Assert.assertEquals(listScheduleDTO.get(0).getStartDateTime(), scheduleDTO.getStartDateTime());
        Assert.assertEquals(listScheduleDTO.get(0).getEndDateTime(), scheduleDTO.getEndDateTime());
    }

    @Test
    @DisplayName("Test verify the method of service ScheduleService - FindSchedule -")
    public void verifyFindScheduleTest() {
        ScheduleDTO scheduleDTO = Fixtures.buildScheduleDTO();
        given(scheduleService.findSchedule(Fixtures.buildSchedule().getId())).willReturn(scheduleDTO);

        ScheduleDTO scheduleDTO1 =  scheduleService.findSchedule(scheduleDTO.getId());
        Assert.assertEquals(scheduleDTO1.getTennisCourt(), scheduleDTO.getTennisCourt());
        Assert.assertEquals(scheduleDTO1.getStartDateTime(), scheduleDTO.getStartDateTime());
        Assert.assertEquals(scheduleDTO1.getEndDateTime(), scheduleDTO.getEndDateTime());
    }

    @Test
    @DisplayName("Test verify the method of service ScheduleService - FindSchedulesByTennisCourtId -")
    public void verifyFindSchedulesByTennisCourtId() {
        ScheduleDTO scheduleDTO = Fixtures.buildScheduleDTO();
        given(scheduleService.findSchedule(Fixtures.buildSchedule().getId())).willReturn(scheduleDTO);

        List<ScheduleDTO> listScheduleDTO =  scheduleService.findSchedulesByTennisCourtId(scheduleDTO.getId());
        Assert.assertEquals(listScheduleDTO.get(0).getTennisCourt(), scheduleDTO.getTennisCourt());
        Assert.assertEquals(listScheduleDTO.get(0).getStartDateTime(), scheduleDTO.getStartDateTime());
        Assert.assertEquals(listScheduleDTO.get(0).getEndDateTime(), scheduleDTO.getEndDateTime());
    }
}

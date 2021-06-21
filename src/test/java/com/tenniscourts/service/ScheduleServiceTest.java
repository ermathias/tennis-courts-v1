package com.tenniscourts.service;

import com.tenniscourts.Fixtures;
import com.tenniscourts.repository.ScheduleRepository;
import com.tenniscourts.repository.TennisCourtRepository;
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
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.Silent.class)
@ContextConfiguration(classes = GuestService.class)
@SpringBootTest
public class ScheduleServiceTest {

    @Mock
    private ScheduleService scheduleService;

    @Mock
    private TennisCourtRepository tennisCourtRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Test
    @DisplayName("Test verify the method of service ScheduleService - AddSchedule -")
    public void verifyAddScheduleTest() {
        LocalDateTime startDateTime = LocalDateTime.of(2021, 2, 13, 15, 56);
        LocalDateTime endDateTime = LocalDateTime.of(2021, 2, 13, 16, 56);
        ScheduleDTO scheduleDTO = Fixtures.buildScheduleDTO();
        given(tennisCourtRepository.findById(1L)).willReturn(Optional.of(Fixtures.buildTennisCourt()));
        given(scheduleRepository.save(Fixtures.buildSchedule())).willReturn(Fixtures.buildSchedule());
        given(scheduleRepository.findByTennisCourtIdAndStartDateTime(1L,startDateTime))
        .willReturn(Fixtures.buildSchedule());

        given(scheduleService.addSchedule(1L, Fixtures.buildCreateScheduleRequestDTO())).willReturn(scheduleDTO);

        CreateScheduleRequestDTO createScheduleRequestDTO = CreateScheduleRequestDTO.builder()
                .startDateTime(startDateTime)
                .startDateTime(endDateTime)
                .tennisCourtId(1L)
                .build();
        ScheduleDTO scheduleDTO1 = scheduleService.addSchedule(1L, createScheduleRequestDTO);
        Assert.assertNull(scheduleDTO1);
    }

    @Test
    @DisplayName("Test verify the method of service ScheduleService - FindSchedulesByDates -")
    public void verifyFindSchedulesByDatesTest() {
        LocalDateTime startDateTime = LocalDateTime.of(2021, 2, 13, 15, 56);
        LocalDateTime endDateTime = LocalDateTime.of(2021, 2, 13, 16, 56);
        ScheduleDTO scheduleDTO = Fixtures.buildScheduleDTO();
        given(scheduleRepository.findById(1L)).willReturn(Optional.of(Fixtures.buildSchedule()));
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
        given(scheduleRepository.findById(1L)).willReturn(Optional.of(Fixtures.buildSchedule()));
        given(scheduleService.findSchedule(1L)).willReturn(scheduleDTO);

        ScheduleDTO scheduleDTO1 =  scheduleService.findSchedule(scheduleDTO.getId());
        Assert.assertEquals(scheduleDTO1.getTennisCourt(), scheduleDTO.getTennisCourt());
        Assert.assertEquals(scheduleDTO1.getStartDateTime(), scheduleDTO.getStartDateTime());
        Assert.assertEquals(scheduleDTO1.getEndDateTime(), scheduleDTO.getEndDateTime());
    }

    @Test
    @DisplayName("Test verify the method of service ScheduleService - FindSchedulesByTennisCourtId -")
    public void verifyFindSchedulesByTennisCourtId() {
        ScheduleDTO scheduleDTO = Fixtures.buildScheduleDTO();
        given(scheduleService.findSchedulesByTennisCourtId(1L)).willReturn(List.of(scheduleDTO));

        List<ScheduleDTO> listScheduleDTO =  scheduleService.findSchedulesByTennisCourtId(1L);
        Assert.assertEquals(listScheduleDTO.get(0).getTennisCourt(), scheduleDTO.getTennisCourt());
        Assert.assertEquals(listScheduleDTO.get(0).getStartDateTime(), scheduleDTO.getStartDateTime());
        Assert.assertEquals(listScheduleDTO.get(0).getEndDateTime(), scheduleDTO.getEndDateTime());
    }
}

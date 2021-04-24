package com.tenniscourts.tenniscourts;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.FixtureSchedules;
import com.tenniscourts.schedules.ScheduleService;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    TennisCourtRepository tennisCourtRepository;
    @Mock
    TennisCourtMapper tennisCourtMapper;


    @Test
    public void addTennisCourt() {
        var tennisCourt = FixtureTennisCourt.fixtureTennisCourt();
        var tennisCourtDTO = FixtureTennisCourt.fixtureTennisCourtDTO();

        Mockito.when(tennisCourtMapper.map(tennisCourtDTO)).thenReturn(tennisCourt);
        Mockito.when(tennisCourtRepository.saveAndFlush(tennisCourt)).thenReturn(tennisCourt);
        Mockito.when(tennisCourtMapper.map(tennisCourt)).thenReturn(tennisCourtDTO);

        TennisCourtDTO response = tennisCourtService.addTennisCourt(tennisCourtDTO);

        Mockito.verify(tennisCourtRepository).saveAndFlush(tennisCourt);

        assertEquals(tennisCourtDTO, response);
    }

    @Test
    public void findTennisCourtById(){
        var tennisCourt = FixtureTennisCourt.fixtureTennisCourt();
        var tennisCourtDTO = FixtureTennisCourt.fixtureTennisCourtDTO();

        Mockito.when(tennisCourtRepository.findById(1L)).thenReturn(java.util.Optional.of(tennisCourt));
        Mockito.when(tennisCourtMapper.map(tennisCourt)).thenReturn(tennisCourtDTO);

        TennisCourtDTO response = tennisCourtService.findTennisCourtById(1L);

        assertEquals(tennisCourtDTO, response);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findTennisCourtByIdEntityNotFoundException(){
        Mockito.when(tennisCourtRepository.findById(1L)).thenReturn(Optional.empty());
        tennisCourtService.findTennisCourtById(1L);
    }

    @Test
    public void findTennisCourtWithSchedulesById(){
        var tennisCourt = FixtureTennisCourt.fixtureTennisCourt();
        var tennisCourtDTO = FixtureTennisCourt.fixtureTennisCourtDTOWithSchedule();
        var scheduleDTOList = FixtureSchedules.fixtureScheduleDTOList();

        Long id = 1L;

        Mockito.when(scheduleService.findSchedulesByTennisCourtId(id)).thenReturn(scheduleDTOList);
        Mockito.when(tennisCourtRepository.findById(1L)).thenReturn(Optional.of(tennisCourt));
        Mockito.when(tennisCourtMapper.map(tennisCourt)).thenReturn(tennisCourtDTO);
        Mockito.when(tennisCourtService.findTennisCourtById(id)).thenReturn(tennisCourtDTO);

        TennisCourtDTO answer = tennisCourtService.findTennisCourtWithSchedulesById(id);

        assertEquals(tennisCourtDTO, answer);
    }

    @Test
    public void findFreeSchedulesByTennisCourtId(){
        var scheduleDTOList = FixtureSchedules.fixtureScheduleDTOList();
        var tennisCourtsList = FixtureTennisCourt.fixtureTennisCourtList();

        var tennisCourtDTOList = FixtureTennisCourt.fixtureTennisCourtDTOList(scheduleDTOList);

        Mockito.when(tennisCourtRepository.findAll()).thenReturn(tennisCourtsList);
        Mockito.when(scheduleService.findFreeSchedulesByTennisCourtId(1L)).thenReturn(scheduleDTOList);

        List<TennisCourtDTO> response = tennisCourtService.findFreeSchedulesByTennisCourtId();

        assertEquals(tennisCourtDTOList.get(0).getId(), response.get(0).getId());
        assertEquals(tennisCourtDTOList.get(0).getName(), response.get(0).getName());
        assertEquals(tennisCourtDTOList.get(0).getTennisCourtSchedules().size(), response.get(0).getTennisCourtSchedules().size());
    }



}
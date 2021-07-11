package com.tenniscourts.service;

import com.tenniscourts.dto.GuestDTO;
import com.tenniscourts.dto.TennisCourtDTO;
import com.tenniscourts.mapper.TennisCourtMapper;
import com.tenniscourts.model.Guest;
import com.tenniscourts.model.TennisCourt;
import com.tenniscourts.repository.TennisCourtRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = TennisCourtService.class)
public class TennisCourtServiceTest {

    @Mock
    private TennisCourtRepository tennisCourtRepository;

    @Mock
    private TennisCourtMapper tennisCourtMapper;

    @Mock
    private ScheduleService scheduleService;


    private TennisCourtService tennisCourtService;



    @Before
    public void setup(){
        tennisCourtService = new TennisCourtService(
                tennisCourtRepository, scheduleService, tennisCourtMapper);
    }

    @Test
    public void addTennisCourtTest() {
        TennisCourtDTO tennisCourtDTO = new TennisCourtDTO();
        tennisCourtDTO.setId(1L);
        tennisCourtDTO.setName("Wimbledon");


        TennisCourt tennisCourt = new TennisCourt();
        tennisCourtDTO.setId(1L);
        tennisCourtDTO.setName("Wimbledon");

        when(tennisCourtRepository.saveAndFlush(any())).thenReturn(tennisCourt);

        when(tennisCourtMapper.map(any(TennisCourt.class))).thenReturn(tennisCourtDTO);

        TennisCourtDTO tennisCourtDTOResult = tennisCourtService.addTennisCourt(tennisCourtDTO);

        Assert.assertEquals(tennisCourtDTOResult.getName(), "Wimbledon");


    }


    @Test
    public void cancelTennisCourtTest() {

        TennisCourtDTO tennisCourtDTO = new TennisCourtDTO();
        tennisCourtDTO.setId(1L);
        tennisCourtDTO.setName("Wimbledon");

        Optional<TennisCourt> tennisCourt = Optional.of(new TennisCourt());
        tennisCourt.get().setId(1L);
        tennisCourt.get().setName("Wimbledon");
        when(tennisCourtRepository.getOne(any())).thenReturn(tennisCourt.get());
        when(tennisCourtMapper.map(any(TennisCourt.class))).thenReturn(tennisCourtDTO);
        TennisCourtDTO tennisCourtDTOResult = tennisCourtService.deleteTennisCourtById(1L);

        Assert.assertEquals(tennisCourtDTOResult.getName(), "Wimbledon");

    }


    @Test
    public void findTennisCourtTest() {

        TennisCourtDTO tennisCourtDTO = new TennisCourtDTO();
        tennisCourtDTO.setId(1L);
        tennisCourtDTO.setName("Wimbledon");

        Optional<TennisCourt> tennisCourt = Optional.of(new TennisCourt());
        tennisCourt.get().setId(1L);
        tennisCourt.get().setName("Wimbledon");
        when(tennisCourtRepository.findById(any())).thenReturn(tennisCourt);
        when(tennisCourtMapper.map(any(TennisCourt.class))).thenReturn(tennisCourtDTO);
        TennisCourtDTO tennisCourtDTOResult = tennisCourtService.
                findTennisCourtById(1L);
        Assert.assertEquals(tennisCourtDTOResult.getName(), "Wimbledon");

    }


}

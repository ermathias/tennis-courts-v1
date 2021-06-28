package com.tenniscourts.tenniscourts;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.ScheduleDTO;
import com.tenniscourts.schedules.ScheduleService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = TennisCourtService.class)
public class TennisCourtServiceTest {

    @InjectMocks
    TennisCourtService tennisCourtService;

    @Mock
    TennisCourtRepository tennisCourtRepository;

    @Mock
    ScheduleService scheduleService;

    @Mock
    TennisCourtMapper tennisCourtMapper;

    @Test(expected = EntityNotFoundException.class)
    public void findTennisCourtByIdThrowsEntityNotFoundExceptionWhenTennisCourtIsNotFound() {
        when(tennisCourtRepository.findById(1L)).thenReturn(Optional.empty());

        tennisCourtService.findTennisCourtById(1L, false);
    }

    @Test
    public void findTennisCourtByIdReturnsTennisCourtWithSchedules() {
        TennisCourt tennisCourt = new TennisCourt();
        tennisCourt.setName("name");
        TennisCourtDTO tennisCourtDTO = new TennisCourtDTO();
        tennisCourtDTO.setName("name");
        List<ScheduleDTO> expected = Arrays.asList(new ScheduleDTO(), new ScheduleDTO());

        when(tennisCourtRepository.findById(1L)).thenReturn(Optional.of(tennisCourt));
        when(tennisCourtMapper.map(tennisCourt)).thenReturn(tennisCourtDTO);
        when(scheduleService.findSchedulesByTennisCourtId(1L)).thenReturn(expected);

        tennisCourtService.findTennisCourtById(1L, true);

        Assert.assertEquals(expected, tennisCourtService.findTennisCourtById(1L, true).getTennisCourtSchedules());
    }
}

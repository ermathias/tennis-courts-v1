package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.AlreadyExistsEntityException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

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

    private CreateScheduleRequestDTO createScheduleRequestDTO;

    @Before
    public void init() {
        createScheduleRequestDTO = CreateScheduleRequestDTO
                .builder()
                .tennisCourtId(1L)
                .startDateTime(LocalDateTime.now())
                .build();
    }

    @Test(expected = EntityNotFoundException.class)
    public void addScheduleThrowsEntityNotFoundExceptionWhenTennisCourtIsNotFound() {
        when(tennisCourtRepository.findById(1L)).thenReturn(Optional.empty());

        scheduleService.addSchedule(createScheduleRequestDTO);
    }

    @Test(expected = AlreadyExistsEntityException.class)
    public void addScheduleThrowsAlreadyExistsEntityExceptionWhenAScheduleWithTheSameDateIsInTheDatabase() {
        TennisCourt tennisCourt = new TennisCourt();

        when(tennisCourtRepository.findById(1L)).thenReturn(Optional.of(tennisCourt));
        when(
                scheduleRepository.countAllByStartDateTimeAndTennisCourt(
                        createScheduleRequestDTO.getStartDateTime().withMinute(0),tennisCourt)
        ).thenReturn(1L);

        scheduleService.addSchedule(createScheduleRequestDTO);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findScheduleByIdThrowsEntityNotFoundExceptionWhenScheduleIsNotFound() {
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());

        scheduleService.findScheduleById(1L);
    }

}

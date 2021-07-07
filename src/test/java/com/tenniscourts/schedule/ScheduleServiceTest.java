package com.tenniscourts.schedule;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestMapper;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.guests.GuestService;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleMapper;
import com.tenniscourts.schedules.ScheduleRepository;
import com.tenniscourts.schedules.ScheduleService;
import org.apache.tomcat.jni.Local;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ScheduleService.class)
public class ScheduleServiceTest {

    @InjectMocks
    ScheduleService scheduleService;

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    ScheduleMapper scheduleMapper;

    @Test(expected = EntityNotFoundException.class)
    public void findScheduleByIdThrowsEntityNotFoundExceptionWhenScheduleIsNotFound() {
        Mockito.when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());

        scheduleService.findSchedule(1L);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findScheduleByDatesThrowsEntityNotFoundExceptionWhenScheduleIsNotFound() {
        LocalDateTime date = LocalDateTime.now();
        Mockito.when(scheduleRepository.findAllByStartDateTimeAfterAndEndDateTimeBefore(date, date))
                .thenThrow(new EntityNotFoundException("Not found"));

       scheduleService.findSchedulesByDates(date, date );
    }

}
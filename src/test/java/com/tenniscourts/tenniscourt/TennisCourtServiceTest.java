package com.tenniscourts.tenniscourt;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestMapper;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.guests.GuestService;
import com.tenniscourts.tenniscourts.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(MockitoJUnitRunner.Silent.class)
@ContextConfiguration(classes = TennisCourtService.class)
public class TennisCourtServiceTest {

    @InjectMocks
    TennisCourtService tennisCourtService;

    @Mock
    TennisCourtRepository tennisCourtRepository;

    @Mock
    TennisCourtMapper tennisCourtMapper;

    @Test(expected = EntityNotFoundException.class)
    public void findTennisCourtByIdThrowsEntityNotFoundExceptionWhenTennisCourtIsNotFound() {
        Mockito.when(tennisCourtRepository.findById(1L)).thenReturn(Optional.empty());

        tennisCourtService.findTennisCourtById(1L);
    }

    @Test
    public void createTennisCourtTest()
    {
        TennisCourtDTO tc = new TennisCourtDTO();
        tc.setId(1L);
        tc.setName("Tennis Club");

        Mockito.when(tennisCourtService.addTennisCourt(tc)).thenReturn(tc);
    }
}
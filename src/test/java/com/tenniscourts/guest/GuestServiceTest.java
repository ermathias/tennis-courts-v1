package com.tenniscourts.guest;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = GuestService.class)
public class GuestServiceTest {

    @InjectMocks
    GuestService guestService;

    @Mock
    GuestRepository guestRepository;

    @Mock
    GuestMapper guestMapper;

    @Test(expected = EntityNotFoundException.class)
    public void deleteGuestThrowsEntityNotFoundExceptionWhenGuestIsNotFound() {

        Mockito.when(guestRepository.findById(1L)).thenReturn(Optional.empty());

        guestService.deleteGuest(1L);
    }
    @Test
    public void findAllGuestTestSizeAndNumberOfInvocations() {
        List<Guest> list = new ArrayList<>();
        Guest g1 = new Guest();
        g1.setId(1L);
        g1.setName("mario");
        Guest g2 = new Guest();
        g1.setId(2L);
        g1.setName("john");
        Guest g3 = new Guest();
        g1.setId(3L);
        g1.setName("doe");

        list.add(g1);
        list.add(g2);
        list.add(g3);

        Mockito.when(guestRepository.findAll()).thenReturn(list);

        //test
        List<Guest> guestList = guestRepository.findAll();

        Assert.assertEquals(3, guestList.size());
        Mockito.verify(guestRepository, times(1)).findAll();
    }

    @Test(expected = EntityNotFoundException.class)
    public void findGuestByIdThrowsEntityNotFoundExceptionWhenGuestIsNotFound() {
        Mockito.when(guestRepository.findById(1L)).thenReturn(Optional.empty());

        guestService.findGuestById(1L);
    }
}
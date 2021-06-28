package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.mockito.Mockito.when;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    public void updateGuestThrowsEntityNotFoundExceptionWhenGuestIsNotFound() {
        GuestDTO guestDTO = GuestDTO.builder().id(1L).build();

        when(guestRepository.findById(1L)).thenReturn(Optional.empty());

        guestService.updateGuest(guestDTO);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteGuestThrowsEntityNotFoundExceptionWhenGuestIsNotFound() {
        when(guestRepository.findById(1L)).thenReturn(Optional.empty());

        guestService.deleteGuest(1L);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findGuestByIdThrowsEntityNotFoundExceptionWhenGuestIsNotFound() {
        when(guestRepository.findById(1L)).thenReturn(Optional.empty());

        guestService.findGuestById(1L);
    }
}

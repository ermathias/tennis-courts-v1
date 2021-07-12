package com.tenniscourts.guests;


import com.tenniscourts.exceptions.EntityNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


import java.util.Objects;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = GuestService.class)
public class GuestServiceTest {

    private final static Long GUEST_ID = 1L;
    private final static String GUEST_NAME = "Marc";

    @Mock
    GuestRepository guestRepository;

    @Mock
    GuestMapper guestMapper;

    @InjectMocks
    GuestService guestService;

    @Test
    public void test_findGuestById_expect_valid_return() {

        Guest guest = new Guest();
        guest.setName(GUEST_NAME);
        guest.setId(GUEST_ID);

        GuestDTO requestGuestDTO = new GuestDTO();
        requestGuestDTO.setName(GUEST_NAME);
        requestGuestDTO.setId(GUEST_ID);

        when(guestRepository.findById(GUEST_ID)).thenReturn(Optional.of(guest));
        when(guestService.findGuestById(requestGuestDTO.getId())).thenReturn(requestGuestDTO);

        GuestDTO returnedGuestDTO = guestService.findGuestById(requestGuestDTO.getId());

        Assert.assertEquals(requestGuestDTO.getId(), returnedGuestDTO.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void test_findById_expect_EntityNotFoundException() {
        when(guestRepository.findById(GUEST_ID)).thenReturn(Optional.empty());

        guestService.findGuestById(GUEST_ID);
    }

    @Test
    public void test_addGuest_expect_valid_return() {

        Guest guest = new Guest();
        guest.setName(GUEST_NAME);

        GuestDTO requestGuestDTO = mock(GuestDTO.class);
        requestGuestDTO.setName(GUEST_NAME);
        requestGuestDTO.setId(GUEST_ID);

        when(guestService.addGuest(requestGuestDTO)).thenReturn(requestGuestDTO);

        GuestDTO savedGuestDTO = guestService.addGuest(requestGuestDTO);

        Assert.assertTrue(Objects.nonNull(savedGuestDTO.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void test_deleteGuestById_expect_EntityNotFoundException() {
        when(guestRepository.findById(GUEST_ID)).thenReturn(Optional.empty());

        guestService.deleteGuestById(GUEST_ID);
    }

    @Test
    public void test_deleteGuestById_expect_entity_deleted() {

        Guest guest = new Guest();
        guest.setName(GUEST_NAME);
        guest.setId(GUEST_ID);

        when(guestRepository.findById(GUEST_ID)).thenReturn(Optional.of(guest));

        guestService.deleteGuestById(GUEST_ID);

        verify(guestRepository, times(1)).delete(guest);
    }
}

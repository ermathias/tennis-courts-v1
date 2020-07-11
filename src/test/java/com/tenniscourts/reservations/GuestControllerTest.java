package com.tenniscourts.reservations;

import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestController;
import com.tenniscourts.guests.GuestDTO;
import com.tenniscourts.guests.GuestService;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = GuestController.class)
        public class GuestControllerTest {

    @InjectMocks
    GuestController guestController;

    @Mock
    GuestService guestService;


    @Test
    public void testAddGuest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Guest guest = new Guest();
        guest.setName("juliano");
        guest.setId(1L);

        when(guestService.addGuest(any(GuestDTO.class))).thenReturn(guest);

        ResponseEntity<Guest> guestResponseEntity = guestController.addGuest(new GuestDTO());

        Assert.assertEquals(guestResponseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testFindGuestById() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));


        Guest guest = new Guest();
        guest.setName("juliano");
        guest.setId(1L);

        when(guestService.findGuestById(any())).thenReturn(guest);

        ResponseEntity<Guest> guestById = guestController.findGuestById(9L);

        Assert.assertEquals(guestById.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(guestById.getBody().getName(), "juliano");
    }
}

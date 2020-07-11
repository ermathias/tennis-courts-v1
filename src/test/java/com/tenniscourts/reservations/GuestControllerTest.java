package com.tenniscourts.reservations;

import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestController;
import com.tenniscourts.guests.GuestDTO;
import com.tenniscourts.guests.GuestService;
import org.assertj.core.api.Assertions;
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

import java.util.Arrays;
import java.util.List;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
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

    @Test
    public void testFindGuestByIdReturnNotFound() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(guestService.findGuestById(any())).thenReturn(null);

        ResponseEntity<Guest> guestById = guestController.findGuestById(9L);

        Assert.assertEquals(guestById.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testfindAllGuest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Guest guest = new Guest();
        guest.setName("Gustavo Kuerten");
        guest.setId(1L);

        Guest guest1 = new Guest();
        guest1.setName("Fernando Meligeni");
        guest1.setId(2L);

        List<Guest> guestListMock = Arrays.asList(guest, guest1);

        when(guestService.findAllGuest()).thenReturn(guestListMock);

        ResponseEntity<List<Guest>> allGuests = guestController.findAllGuest();

        List<Guest> returnedGuestList = allGuests.getBody();

        Assertions.assertThat(returnedGuestList).contains(guest, guest1);
    }

    @Test
    public void testFindGuestByName() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Guest guest = new Guest();
        guest.setName("juliano");
        guest.setId(1L);

        when(guestService.findGuestByName(any())).thenReturn(guest);

        ResponseEntity<Guest> guestByName = guestController.findGuestByName("juliano");

        Assert.assertEquals(guestByName.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(guestByName.getBody().getName(), "juliano");
    }

    @Test
    public void testupdateGuest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Guest guestDB = new Guest();
        guestDB.setName("pedro");
        guestDB.setId(1L);

        Guest guestUpdt = new Guest();
        guestUpdt.setName("juliano");
        guestUpdt.setId(1L);


        when(guestService.findGuestById(any())).thenReturn(guestDB);
        when(guestService.updateGuest(guestDB, "juliano")).thenReturn(guestUpdt);

        ResponseEntity<Guest> updatedGuestResponse = guestController.upateGuestById(1L, "juliano");

        Assert.assertEquals(updatedGuestResponse.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(updatedGuestResponse.getBody().getName(), "juliano");
    }


    @Test
    public void testDeleteGuestById() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Guest guestDB = new Guest();
        guestDB.setName("juliano");
        guestDB.setId(1L);

        when(guestService.findGuestById(any())).thenReturn(guestDB);
        doNothing().when(guestService).deleteGuestById(1L);

        ResponseEntity<Guest> guestById = guestController.deleteGuestById(1L);

        Assert.assertEquals(guestById.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testDeleteGuestByIdWhenIdNotPresentAtDB() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(guestService.findGuestById(any())).thenReturn(null);

        ResponseEntity<Guest> guestById = guestController.deleteGuestById(1L);

        Assert.assertEquals(guestById.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}

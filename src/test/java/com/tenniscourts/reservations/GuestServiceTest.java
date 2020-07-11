package com.tenniscourts.reservations;

import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestDTO;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.guests.GuestService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = GuestService.class)
public class GuestServiceTest {

    @InjectMocks
    GuestService guestService;

    @Mock
    GuestRepository guestRepository;

    @Test
    public void addGuestTest() {
        GuestDTO guestDTO = new GuestDTO("juliano");

        Guest guest = new Guest("juliano");
        guest.setId(1L);

//        when(guestRepository.save(guest)).thenReturn(guest);

        Guest finalGuest = guestService.addGuest(guestDTO);

        Assertions.assertEquals(finalGuest.getName(), guest.getName());
    }

    @Test
    public void findGuestByIdTest() {
        Guest guest = new Guest("juliano");
        guest.setId(1L);
        Optional<Guest> guestOptl = Optional.of(guest);

        when(guestRepository.findById(1L)).thenReturn(guestOptl);

        Guest guestById = guestService.findGuestById(1L);

        Assertions.assertEquals(guest, guestById);
    }

    @Test
    public void findGuestByIdWhenIdNotFoundTest() {
        Guest guest = null;
        Optional<Guest> guestOptl = Optional.ofNullable(guest);

        when(guestRepository.findById(1L)).thenReturn(guestOptl);
        Guest guestById = guestService.findGuestById(1L);

        Assertions.assertEquals(null, guestById);
    }

    @Test
    public void testfindAllGuest() {
        Guest guest = new Guest();
        guest.setName("Gustavo Kuerten");
        guest.setId(1L);

        Guest guest1 = new Guest();
        guest1.setName("Fernando Meligeni");
        guest1.setId(2L);

        List<Guest> guestListMock = Arrays.asList(guest, guest1);

        when(guestRepository.findAll()).thenReturn(guestListMock);

        List<Guest> allGuest = guestService.findAllGuest();

        org.assertj.core.api.Assertions.assertThat(allGuest).contains(guest, guest1);
    }

    @Test
    public void testFindGuestByName() {
        Guest guestDB = new Guest();
        guestDB.setName("juliano");
        guestDB.setId(1L);

        when(guestRepository.findByName(any())).thenReturn(Optional.of(guestDB));

        Guest guest = guestService.findGuestByName("juliano");

        Assert.assertEquals(guest, guestDB);
    }

    @Test
    public void testupdateGuest() {
        Guest guestDB = new Guest();
        guestDB.setName("pedro");
        guestDB.setId(1L);

        Guest guestUpdt = new Guest();
        guestUpdt.setName("juliano");
        guestUpdt.setId(1L);

        when(guestRepository.save(any())).thenReturn(guestUpdt);

        Guest guest = guestService.updateGuest(guestDB, "juliano");

        Assertions.assertEquals(guest, guestUpdt);
    }
}

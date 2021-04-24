package com.tenniscourts.guests;

import com.tenniscourts.exceptions.AlreadyExistsEntityException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void addGuest(){
        var guest = FixtureGuest.fixtureGuestBuilder();
        var guestDTO = FixtureGuest.fixtureGuestDTO();

        Mockito.when(guestRepository.findByName(guest.getName())).thenReturn(Optional.empty());
        Mockito.when(guestMapper.map(guest)).thenReturn(guestDTO);
        Mockito.when(guestRepository.saveAndFlush(guest)).thenReturn(guest);

        GuestDTO response =  guestService.addGuest("Nadal");

        Mockito.verify(guestRepository).saveAndFlush(guest);

        assertEquals(guestDTO, response);
    }

    @Test(expected = AlreadyExistsEntityException.class)
    public void addGuestAlreadyExistsEntityException(){
        var guest = FixtureGuest.fixtureGuest();
        var guestDTO = FixtureGuest.fixtureGuestDTO();

        Mockito.when(guestRepository.findByName(guest.getName())).thenReturn(Optional.of(guest));

        GuestDTO response =  guestService.addGuest("Nadal");

        Mockito.verify(guestRepository).saveAndFlush(guest);

        assertEquals(guestDTO, response);
    }

    @Test
    public void updateGuest(){
        var guest = FixtureGuest.fixtureGuest();
        var guestDTO = FixtureGuest.fixtureGuestDTO();

        Mockito.when(guestMapper.map(guestDTO)).thenReturn(guest);
        Mockito.when(guestMapper.map(guest)).thenReturn(guestDTO);
        Mockito.when(guestRepository.save(guest)).thenReturn(guest);

        GuestDTO response =  guestService.updateGuest(guestDTO);

        Mockito.verify(guestRepository).save(guest);

        assertEquals(guestDTO, response);

    }

    @Test
    public void findGuestById(){
        var guest = FixtureGuest.fixtureGuest();
        var guestDTO = FixtureGuest.fixtureGuestDTO();

        Mockito.when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));
        Mockito.when(guestMapper.map(guest)).thenReturn(guestDTO);

        GuestDTO response = guestService.findByGuestId(1L);

        assertEquals(guestDTO, response);
    }

    @Test
    public void findAllGuest(){
        var guest = FixtureGuest.fixtureGuest();
        var secondGuest = FixtureGuest.fixtureOtherGuest();

        List<Guest> listEnt = Arrays.asList(guest, secondGuest);

        var guestDTO = FixtureGuest.fixtureGuestDTO();
        var otherGuestDTO = FixtureGuest.fixtureOtherGuestDTO();
        List<GuestDTO> listDto = Arrays.asList(guestDTO, otherGuestDTO);

        Mockito.when(guestRepository.findAll()).thenReturn(listEnt);
        Mockito.when(guestMapper.map(listEnt)).thenReturn(listDto);

        List<GuestDTO> response = guestService.listAllGuests();

        assertEquals(listDto, response);

    }

    @Test
    public void findGuestsByName(){
        var guest = FixtureGuest.fixtureGuest();
        var guestDTO = FixtureGuest.fixtureGuestDTO();

        Mockito.when(guestRepository.findByName("Nadal")).thenReturn(Optional.of(guest));
        Mockito.when(guestMapper.map(guest)).thenReturn(guestDTO);

        GuestDTO response = guestService.findByGuestName("Nadal");

        assertEquals(guestDTO, response);

    }

    @Test
    public void deleteGuest(){
        var guest = FixtureGuest.fixtureGuest();

        Mockito.when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));

        guestService.deleteGuest(guest.getId());

        Mockito.verify(guestRepository).delete(guest);
    }

}

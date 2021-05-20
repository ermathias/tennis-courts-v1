package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.reservations.ReservationService;
import org.junit.Assert;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.isA;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ReservationService.class)
public class GuestServiceTest {

    @InjectMocks
    GuestService guestService = new GuestService();

    @Mock
    GuestRepository guestRepository;

    @Mock
    GuestMapper guestMapper;

    @Test
    public void addGuest() {
        GuestDTO guest = new GuestDTO();
        guest.setName("TestUser");
        Guest guest1 = new Guest();
        guest1.setId(1L);
        guest1.setName(guest.getName());
        Mockito.when(guestRepository.save(isA(Guest.class)))
                .thenReturn(guest1);
        Mockito.when(guestMapper.map(isA(GuestDTO.class)))
                .thenReturn(guest1);
        Mockito.when(guestMapper.map(isA(Guest.class)))
                .thenReturn(guest);
        Assert.assertEquals(guestService.addGuest(guest).getName(),guest.getName());
    }

    @Test
    public void updateGuest(){
        GuestDTO guest = new GuestDTO();
        guest.setName("Test");
        guest.setId(1L);
        Optional<Guest> guestObj = Optional.of(new Guest("Test"));
        Mockito.when(guestMapper.map(isA(Guest.class)))
                .thenReturn(guest);
        Mockito.when(guestRepository.findById(isA(Long.class))).thenReturn(guestObj);
        Assert.assertEquals(guestService.updateGuest(guest).getName(),guest.getName());
    }

    @Test
    public void findGuestById(){
        Guest guest = new Guest();
        guest.setName("Test");
        Mockito.when(guestRepository.findById(isA(Long.class)))
                .thenReturn(Optional.of(guest));
        Mockito.when(guestMapper.map(isA(Guest.class))).thenReturn(new GuestDTO(1L,"Test"));
        Assert.assertEquals(guestService.findGuestById(1L).getName(),guest.getName());
    }

    @Test
    public void findGuestByName(){
        List<Guest> guestList = new ArrayList<>();
        guestList.add(new Guest("Test"));
        Mockito.when(guestRepository.findByName(isA(String.class)))
                .thenReturn(Optional.of(guestList));
        List<GuestDTO> guestDtoList = new ArrayList<>();
        guestDtoList.add(new GuestDTO(1L,"Test"));
        Mockito.when(guestMapper.map(isA(List.class))).thenReturn(guestDtoList);
        Assert.assertEquals(guestService.findGuestByName("Test").get(0).getName(),
                guestList.get(0).getName());
    }

    @Test
    public void updateGuestEntityNotFound() {
        try {
            Mockito.when(guestRepository.findById(isA(Long.class)))
                    .thenReturn(Optional.empty());
            guestService.updateGuest(new GuestDTO(1L,"Test"));
        } catch (EntityNotFoundException ex) {
            Assert.assertEquals("Guest not found.", ex.getMessage());
        }
    }

    @Test
    public void findGuestByIdEntityNotFound() {
        try {
            Mockito.when(guestRepository.findById(isA(Long.class)))
                    .thenReturn(Optional.empty());
            guestService.findGuestById(1L);
        } catch (EntityNotFoundException ex) {
            Assert.assertEquals("GuestId not found.", ex.getMessage());
        }
    }

    @Test
    public void findGuestByNameEntityNotFound() {
        try {
            Mockito.when(guestRepository.findByName(isA(String.class)))
                    .thenReturn(Optional.empty());
            guestService.findGuestByName("Test");
        } catch (EntityNotFoundException ex) {
            Assert.assertEquals("GuestName not found.", ex.getMessage());
        }
    }

    @Test
    public void fetchAll() {
        List<Guest> guestList = new ArrayList<>();
        Guest guest = new Guest("Test");
        guestList.add(guest);

        List<GuestDTO> guestDTOS = new ArrayList<>();
        GuestDTO guestDto = new GuestDTO(1L,"Test");
        guestDTOS.add(guestDto);

        Mockito.when(guestRepository.findAll()).thenReturn(guestList);
        Mockito.when(guestMapper.map(guestList))
                .thenReturn(guestDTOS);
        Assert.assertEquals(guestService.fetchAll().get(0).getName(),guest.getName());

    }

    @Test
    public void deleteGuest(){
        List<GuestDTO> guestDTOS = new ArrayList<>();
        Mockito.when(guestMapper.map(isA(List.class))).thenReturn(guestDTOS);
        Assert.assertEquals(guestService.deleteGuest(1L),guestDTOS);
    }
}

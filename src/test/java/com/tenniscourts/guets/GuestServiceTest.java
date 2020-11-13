package com.tenniscourts.guets;

import com.tenniscourts.guests.*;
import com.tenniscourts.schedules.CreateScheduleRequestDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
    public void addGuestNotAdminUser(){
        Guest guest = new Guest();
        guest.setAdmin(false);
        guest.setName("Nadal");
        guest.setId(1L);

        Mockito.when(guestRepository.getOne(1L)).thenReturn(guest);

        try {
            guestService.addGuest(1L, new GuestDTO());
            fail("Should have thrown an exception");
        } catch (UnsupportedOperationException e){
            assertEquals("Only admin users are allowed to do that.", e.getMessage());
        }
    }

    @Test
    public void addGuest(){
        Guest guest = new Guest();
        guest.setAdmin(true);
        guest.setName("Nadal");
        guest.setId(1L);

        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setId(1L);
        guestDTO.setName("nadal");


        Mockito.when(guestRepository.getOne(1L)).thenReturn(guest);
        Mockito.when(guestMapper.map(guestDTO)).thenReturn(guest);
        Mockito.when(guestMapper.map(guest)).thenReturn(guestDTO);
        Mockito.when(guestRepository.saveAndFlush(guest)).thenReturn(guest);

        GuestDTO answer =  guestService.addGuest(1L, guestDTO);

        Mockito.verify(guestRepository).saveAndFlush(guest);

        assertEquals(guestDTO, answer);

    }

    @Test
    public void updateGuestNotAdminUser(){
        Guest guest = new Guest();
        guest.setAdmin(false);
        guest.setName("Nadal");
        guest.setId(1L);

        Mockito.when(guestRepository.getOne(1L)).thenReturn(guest);

        try {
            guestService.updateGuest(1L, new GuestDTO());
            fail("Should have thrown an exception");
        } catch (UnsupportedOperationException e){
            assertEquals("Only admin users are allowed to do that.", e.getMessage());
        }
    }

    @Test
    public void updateGuest(){
        Guest guest = new Guest();
        guest.setAdmin(true);
        guest.setName("Nadal");
        guest.setId(1L);

        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setId(1L);
        guestDTO.setName("nadal");


        Mockito.when(guestRepository.getOne(1L)).thenReturn(guest);
        Mockito.when(guestMapper.map(guestDTO)).thenReturn(guest);
        Mockito.when(guestMapper.map(guest)).thenReturn(guestDTO);
        Mockito.when(guestRepository.save(guest)).thenReturn(guest);

        GuestDTO answer =  guestService.updateGuest(1L, guestDTO);

        Mockito.verify(guestRepository).save(guest);

        assertEquals(guestDTO, answer);

    }

    @Test
    public void findGuestById(){
        Guest guest = new Guest();
        guest.setName("Nadal");
        guest.setId(1L);

        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setId(1L);
        guestDTO.setName("Nadal");

        Mockito.when(guestRepository.getOne(1L)).thenReturn(guest);
        Mockito.when(guestMapper.map(guest)).thenReturn(guestDTO);


        GuestDTO answer = guestService.findGuestById(1L);

        assertEquals(guestDTO, answer);
    }

    @Test
    public void findAllGuestNotAdminUser(){
        Guest guest = new Guest();
        guest.setAdmin(false);
        guest.setName("Nadal");
        guest.setId(1L);

        Mockito.when(guestRepository.getOne(1L)).thenReturn(guest);

        try {
            guestService.findAllGuests(1L);
            fail("Should have thrown an exception");
        } catch (UnsupportedOperationException e){
            assertEquals("Only admin users are allowed to do that.", e.getMessage());
        }
    }

    @Test
    public void findAllGuest(){
        Guest guest = new Guest();
        guest.setName("Nadal");
        guest.setId(1L);
        guest.setAdmin(true);
        Guest guest2 = new Guest();
        guest2.setName("Nadal2");
        guest2.setId(2L);
        guest2.setAdmin(false);
        List<Guest> listEnt = Arrays.asList(guest, guest2);

        GuestDTO dto = new GuestDTO();
        dto.setName("Nadal");
        GuestDTO dto2 = new GuestDTO();
        guest2.setName("Nadal2");
        List<GuestDTO> listDto = Arrays.asList(dto, dto2);

        Mockito.when(guestRepository.getOne(1L)).thenReturn(guest);
        Mockito.when(guestRepository.findAll()).thenReturn(listEnt);
        Mockito.when(guestMapper.map(listEnt)).thenReturn(listDto);

        List<GuestDTO> answer = guestService.findAllGuests(1L);

        assertEquals(listDto, answer);

    }

    @Test
    public void findGuestsByNameNotAdminUser(){
        Guest guest = new Guest();
        guest.setAdmin(false);
        guest.setName("Nadal");
        guest.setId(1L);

        Mockito.when(guestRepository.getOne(1L)).thenReturn(guest);

        try {
            guestService.findGuestsByName(1L, "Nadal");
            fail("Should have thrown an exception");
        } catch (UnsupportedOperationException e){
            assertEquals("Only admin users are allowed to do that.", e.getMessage());
        }
    }

    @Test
    public void findGuestsByName(){
        Guest guest = new Guest();
        guest.setName("Nadal");
        guest.setId(1L);
        guest.setAdmin(true);
        Guest guest2 = new Guest();
        guest2.setName("Nadal");
        guest2.setId(2L);
        guest2.setAdmin(false);
        List<Guest> listEnt = Arrays.asList(guest, guest2);

        GuestDTO dto = new GuestDTO();
        dto.setName("Nadal");
        GuestDTO dto2 = new GuestDTO();
        guest2.setName("Nadal");
        List<GuestDTO> listDto = Arrays.asList(dto, dto2);

        Mockito.when(guestRepository.getOne(1L)).thenReturn(guest);
        Mockito.when(guestRepository.findByName("Nadal")).thenReturn(listEnt);
        Mockito.when(guestMapper.map(listEnt)).thenReturn(listDto);

        List<GuestDTO> answer = guestService.findGuestsByName(1L, "Nadal");

        assertEquals(listDto, answer);

    }

    @Test
    public void deleteGuestNotAdminUser(){
        Guest guest = new Guest();
        guest.setAdmin(false);
        guest.setName("Nadal");
        guest.setId(1L);

        Mockito.when(guestRepository.getOne(1L)).thenReturn(guest);

        try {
            guestService.deleteGuest(1L, 1L);
            fail("Should have thrown an exception");
        } catch (UnsupportedOperationException e){
            assertEquals("Only admin users are allowed to delete guests.", e.getMessage());
        }
    }

    @Test
    public void deleteGuest(){
        Guest guest = new Guest();
        guest.setAdmin(true);
        guest.setName("Nadal");
        guest.setId(1L);

        Mockito.when(guestRepository.getOne(1L)).thenReturn(guest);

        guestService.deleteGuest(1L, 1L);

        Mockito.verify(guestRepository).delete(guest);
    }

}

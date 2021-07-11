package com.tenniscourts.service;

import com.tenniscourts.dto.GuestDTO;
import com.tenniscourts.dto.ScheduleDTO;
import com.tenniscourts.dto.TennisCourtDTO;
import com.tenniscourts.mapper.GuestMapper;
import com.tenniscourts.mapper.TennisCourtMapper;
import com.tenniscourts.model.Guest;
import com.tenniscourts.model.Schedule;
import com.tenniscourts.model.TennisCourt;
import com.tenniscourts.repository.GuestRepository;
import com.tenniscourts.repository.TennisCourtRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ScheduleService.class)
public class GuestServiceTest {

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private GuestMapper guestMapper;

    private GuestService guestService;


    @Before
    public void setup(){
        guestService = new GuestService(
                guestRepository, guestMapper);
    }

    @Test
    public void createGuest() {
        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setId(1L);
        guestDTO.setName("Andre Agassi");

        Guest guest = new Guest();
        guest.setId(1L);
        guest.setName("Andre Agassi");


        when(guestRepository.save(any())).thenReturn(guest);

        when(guestMapper.map(any(Guest.class))).thenReturn(guestDTO);

        GuestDTO guestDTOResult = guestService.create(guestDTO);

        Assert.assertEquals(guestDTOResult.getName(), "Andre Agassi");


    }


    @Test
    public void findGuestByID() {


        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setId(1L);
        guestDTO.setName("Andre Agassi");

        Optional<Guest> guest = Optional.of(new Guest());
        guest.get().setId(1L);
        guest.get().setName("Andre Agassi");
        when(guestRepository.findById(any())).thenReturn(guest);
        when(guestMapper.map(any(Guest.class))).thenReturn(guestDTO);
        GuestDTO guestDTOResult = guestService.
                findById(1L);

        Assert.assertEquals(guestDTOResult.getName(), "Andre Agassi");

    }

    @Test
    public void findGuestByName() {


        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setId(1L);
        guestDTO.setName("Andre Agassi");

        Optional<Guest> guest = Optional.of(new Guest());
        guest.get().setId(1L);
        guest.get().setName("Andre Agassi");
        when(guestRepository.findByName(any())).thenReturn(guest);
        when(guestMapper.map(any(Guest.class))).thenReturn(guestDTO);
        GuestDTO guestDTOResult = guestService.
                findByName("Andre Agassi");

        Assert.assertEquals(guestDTOResult.getName(), "Andre Agassi");

    }


    @Test
    public void cancelGuest() {

        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setId(1L);
        guestDTO.setName("Andre Agassi");

        Optional<Guest> guest = Optional.of(new Guest());
        guest.get().setId(1L);
        guest.get().setName("Andre Agassi");
        when(guestRepository.getOne(any())).thenReturn(guest.get());
        when(guestMapper.map(any(Guest.class))).thenReturn(guestDTO);
        GuestDTO guestDTOResult = guestService.
                cancel(1L);

        Assert.assertEquals(guestDTOResult.getName(), "Andre Agassi");

    }


    @Test
    public void findAll() {
        List<GuestDTO> guestDTOs = new ArrayList<>();

        GuestDTO guestDTO1 = new GuestDTO();
        guestDTO1.setId(1L);
        guestDTO1.setName("Andre Agassi");

        GuestDTO guestDTO2 = new GuestDTO();
        guestDTO2.setId(2L);
        guestDTO2.setName("Rogerer Federer");
        guestDTOs.add(guestDTO1);
        guestDTOs.add(guestDTO2);

        List<Guest> guests = new ArrayList<>();
        Optional<Guest> guest1 = Optional.of(new Guest());
        guest1.get().setId(1L);
        guest1.get().setName("Andre Agassi");
        Optional<Guest> guest2 = Optional.of(new Guest());
        guest2.get().setId(2L);
        guest2.get().setName("Rogerer Federer");
        guests.add(guest1.get());
        guests.add(guest2.get());

        when(guestRepository.findAll()).thenReturn(guests);
        when(guestMapper.map(anyList())).thenReturn(guestDTOs);

        List<GuestDTO> listResult = guestService.
                findAll();

        Assert.assertEquals(listResult.size(), 2);

    }





}

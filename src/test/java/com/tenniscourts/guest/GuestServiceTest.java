package com.tenniscourts.guest;

import com.tenniscourts.guests.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = GuestService.class)
public class GuestServiceTest {

  @InjectMocks
  GuestService guestService;
  GuestRepository guestRepository;
  GuestMapper guestMapper;

  @Before
  public void init() {
    this.guestRepository = mock(GuestRepository.class);
    this.guestMapper = mock(GuestMapper.class);
    this.guestService = new GuestService(guestRepository,guestMapper);
  }

  Guest mockGuest(Long id, String name){
    Guest guest = new Guest();
    guest.setName(name);
    guest.setId(id);
    return guest;
  }

  GuestDTO mockGuestDto(Long id, String name){
    GuestDTO guest = new GuestDTO();
    guest.setName(name);
    guest.setId(id);
    return guest;
  }

  List<Guest> buildGuestList(){
    List<Guest> guestList = new ArrayList<>();
    guestList.add(mockGuest(1L, "mockGuest1"));
    guestList.add(mockGuest(2L, "mockGuest2"));
    guestList.add(mockGuest(3L, "OtherGuest1"));
    return guestList;
  }

  List<GuestDTO> buildGuestListDTO(){
    List<GuestDTO> guestList = new ArrayList<>();
    guestList.add(mockGuestDto(1L, "mockGuest1"));
    guestList.add(mockGuestDto(2L, "mockGuest2"));
    guestList.add(mockGuestDto(3L, "OtherGuest1"));
    return guestList;
  }

  @Test
  public void shouldReturnAllGuests() {
    when(guestRepository.findAll()).thenReturn(buildGuestList());
    when(guestMapper.map(any(List.class))).thenReturn(buildGuestListDTO());
    List<GuestDTO> expected = guestService.findAll();
    assertTrue(expected.size()> 0);
    verify(guestRepository).findAll();
  }

  @Test
  public void whenSaveGuest_shouldReturnGuest() {
    Guest guest = mockGuest(1L, "mockGuest1");
    GuestDTO guestDto = mockGuestDto(1L, "mockGuest1");
    when(guestRepository.saveAndFlush(any(Guest.class))).thenReturn(guest);
    when(guestMapper.map(any(GuestDTO.class))).thenReturn(guest);
    when(guestMapper.map(any(Guest.class))).thenReturn(guestDto);
    GuestDTO result = guestService.addGuest(guestDto);
    verify(guestRepository).saveAndFlush(guest);
  }

  @Test
  public void whenGivenId_shouldDeleteGuest_ifFound(){
    Guest guest = mockGuest(1L, "mockGuest1");
    guestService.deleteGuest(guest.getId());
    verify(guestRepository).deleteById(guest.getId());
  }

}

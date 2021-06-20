package com.tenniscourts.service;

import com.tenniscourts.Fixtures;
import com.tenniscourts.storage.CreateGuestDTO;
import com.tenniscourts.storage.GuestDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import static org.mockito.BDDMockito.given;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = GuestService.class)
@SpringBootTest
public class GuestServiceTest {
    @Mock
    private GuestService guestService;

    @Test
    @DisplayName("Test verify the method of service GuestService - FindGuestById -")
    public void verifyFindGuestByIdTest() {
        GuestDTO guest = GuestDTO.builder().name("Buck Rogers").id(1L).build();
        given(guestService.findGuestById(1L)).willReturn(guest);

        GuestDTO guestDTOs = guestService.findGuestById(1L);
        Assert.assertNotNull(guestDTOs.getName());
    }

    @Test
    @DisplayName("Test verify the method of service GuestService - FindGuestByName -")
    public void verifyFindGuestByNameTest() {
        List<GuestDTO> guests = List.of(GuestDTO.builder().name("Aristoteles").id(1L).build());
        given(guestService.findGuestByName("Aristoteles")).willReturn(guests);

        CreateGuestDTO createGuestDTO = CreateGuestDTO.builder().name("Aristoteles").build();
        List<GuestDTO> guestDTOs = guestService.findGuestByName(createGuestDTO.getName());

        Assert.assertEquals((guestDTOs.get(0)).getName(),createGuestDTO.getName());
    }

    @Test
    @DisplayName("Test verify the method of service GuestService - AddGuest -")
    public void verifyAddGuestTest() {
        GuestDTO guest = GuestDTO.builder().name("Aristoteles").build();
        given(guestService.addGuest(Fixtures.buildCreateGuestDTO())).willReturn(guest);

        CreateGuestDTO createGuestDTO = Fixtures.buildCreateGuestDTO();
        GuestDTO guestAdd = guestService.addGuest(createGuestDTO);

        Assert.assertEquals(guestAdd.getName(),guest.getName());
    }

    @Test
    @DisplayName("Test verify the method of service GuestService - UpdateGuest -")
    public void verifyUpdateGuestTest() {
        GuestDTO guestDTO = GuestDTO.builder()
                .name("Poincare")
                .id(1L)
                .build();
        given(guestService.updateGuest(guestDTO)).willReturn(guestDTO);

        GuestDTO guestUpdate = guestService.updateGuest(guestDTO);

        Assert.assertEquals(guestDTO.getName(),guestUpdate.getName());

    }

    @Test
    @DisplayName("Test verify the method of service GuestService - DeleteGuest -")
    public void verifyDeleteGuest() {
        GuestDTO guest = GuestDTO.builder().name("Goedel").id(1L).build();
        given(guestService.deleteGuest(1L)).willReturn(guest);

        GuestDTO guestDTO = guestService.deleteGuest(1L);

        Assert.assertEquals(guestDTO.getName(),guest.getName());
    }
}

package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class GuestServiceTest {

    @InjectMocks
    private GuestService guestService;

    @Mock
    private GuestRepository guestRepository;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = EntityNotFoundException.class)
    @DisplayName("Must throw an EntityNotFoundException when id not found")
    public void updateError(){

        Mockito.when(guestRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        GuestDTO guestDTO = new GuestDTO.GuestDTOBuilder().id(1L).name("João").build();
        guestService.save(guestDTO);
    }
}

package com.tenniscourts.tenniscourts;

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
public class TennisCourtsServiceTest {

    @InjectMocks
    private TennisCourtService tennisCourtService;

    @Mock
    private TennisCourtRepository tennisCourtRepository;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = EntityNotFoundException.class)
    @DisplayName("Must throw an EntityNotFoudException")
    public void findTennisCourtByIdError(){

        Mockito.when(tennisCourtRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        tennisCourtService.findTennisCourtById(1L);
    }


}

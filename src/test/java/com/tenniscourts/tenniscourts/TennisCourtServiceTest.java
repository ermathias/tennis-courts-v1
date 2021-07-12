package com.tenniscourts.tenniscourts;


import com.tenniscourts.exceptions.EntityNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = TennisCourtService.class)
public class TennisCourtServiceTest {

    private final static Long COURT_ID = 1L;
    private final static String COURT_NAME = "TEST_NAME";


    @Mock
    TennisCourtRepository tennisCourtRepository;

    @Mock
    TennisCourtMapper tennisCourtMapper;

    @InjectMocks
    TennisCourtService tennisCourtService;

    @Test(expected = EntityNotFoundException.class)
    public void test_findTennisCourtById__expect_EntityNotFoundException() {
        when(tennisCourtRepository.findById(COURT_ID)).thenReturn(Optional.empty());

        tennisCourtService.findTennisCourtById(COURT_ID);
    }


}

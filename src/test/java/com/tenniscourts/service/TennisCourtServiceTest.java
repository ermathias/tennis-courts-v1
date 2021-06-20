package com.tenniscourts.service;

import com.tenniscourts.Fixtures;
import com.tenniscourts.storage.TennisCourtDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = GuestService.class)
@SpringBootTest
public class TennisCourtServiceTest {

    @Mock
    private TennisCourtService tennisCourtService;

    @Test
    @DisplayName("Test verify the method of service TennisCourtService - AddTennisCourt -")
    public void verifyAddTennisCourtTest() {
        TennisCourtDTO tennisCourtDTO = Fixtures.buildTennisCourtDTO();
        given(tennisCourtService.addTennisCourt(tennisCourtDTO)).willReturn(tennisCourtDTO);

        TennisCourtDTO tennisCourtDTO1 = tennisCourtService.addTennisCourt(tennisCourtDTO);
        Assert.assertEquals(tennisCourtDTO.getName(),tennisCourtDTO1.getName());
    }

    @Test
    @DisplayName("Test verify the method of service TennisCourtService - FindTennisCourtById -")
    public void verifyFindTennisCourtByIdTest() {
        TennisCourtDTO tennisCourtDTO = Fixtures.buildTennisCourtDTO();
        given(tennisCourtService.findTennisCourtById(tennisCourtDTO.getId())).willReturn(tennisCourtDTO);

        TennisCourtDTO tennisCourtDTO1 = tennisCourtService.addTennisCourt(tennisCourtDTO);
        Assert.assertEquals(tennisCourtDTO.getName(),tennisCourtDTO1.getName());
    }

    @Test
    @DisplayName("Test verify the method of service TennisCourtService - FindTennisCourtWithSchedulesById -")
    public void verifyFindTennisCourtWithSchedulesByIdTest() {
        TennisCourtDTO tennisCourtDTO = Fixtures.buildTennisCourtDTO();
        given(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtDTO.getId())).willReturn(tennisCourtDTO);

        TennisCourtDTO tennisCourtDTO1 = tennisCourtService.addTennisCourt(tennisCourtDTO);
        Assert.assertEquals(tennisCourtDTO.getName(),tennisCourtDTO1.getName());
    }
}

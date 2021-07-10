package com.tenniscourts.controller;

import com.tenniscourts.config.AbstractTest;
import com.tenniscourts.config.WebTestConfig;
import com.tenniscourts.dto.ScheduleDTO;
import com.tenniscourts.dto.TennisCourtDTO;
import com.tenniscourts.service.ReservationService;
import com.tenniscourts.service.ScheduleService;
import com.tenniscourts.service.TennisCourtService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class TennisCourtControllerTest extends AbstractTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private TennisCourtService tennisCourtService;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private ScheduleService scheduleService;


    @Test
    public void should_create_tennis_court() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");

        TennisCourtDTO tennisCourtDTO = new TennisCourtDTO();
        tennisCourtDTO.setId(1L);
        when(tennisCourtService.addTennisCourt(any())).thenReturn(tennisCourtDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/tennis-court/add")
                .accept(MEDIA_TYPE_JSON)
                .contentType(MEDIA_TYPE_JSON)
                .content(createTennisCourtRequestJson()))
                .andExpect(status().isCreated());

    }

    @Test
    public void should_find_tennis_court_by_id() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");

        TennisCourtDTO tennisCourtDTO = new TennisCourtDTO();
        tennisCourtDTO.setId(1L);
        when(tennisCourtService.findTennisCourtById(any())).thenReturn(tennisCourtDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/tennis-court/get")
                .accept(MEDIA_TYPE_JSON)
                .contentType(MEDIA_TYPE_JSON)
                .param("tennisCourtId", "1"))
                .andExpect(status().isOk());

    }

    @Test
    public void should_find_tennis_court_with_schedules() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");

        TennisCourtDTO tennisCourtDTO = new TennisCourtDTO();
        tennisCourtDTO.setId(1L);
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        tennisCourtDTO.setTennisCourtSchedules(scheduleDTOList);

        when(tennisCourtService.findTennisCourtById(any())).thenReturn(tennisCourtDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/tennis-court/get-schedules")
                .accept(MEDIA_TYPE_JSON)
                .contentType(MEDIA_TYPE_JSON)
                .param("tennisCourtId", "1"))
                .andExpect(status().isOk());

    }




}

package com.tenniscourts.controller;

import com.tenniscourts.config.AbstractTest;
import com.tenniscourts.config.WebTestConfig;
import com.tenniscourts.dto.ScheduleDTO;
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
public class ScheduleControllerTest extends AbstractTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private TennisCourtService tennisCourtService;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private ScheduleService scheduleService;

    @Test
    public void should_add_schedule() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(1L);
        when(scheduleService.addSchedule(any(), any())).thenReturn(scheduleDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/schedule/add")
                .accept(MEDIA_TYPE_JSON)
                .contentType(MEDIA_TYPE_JSON)
                .content(createScheduleRequestJson()))
                .andExpect(status().isCreated());

    }


    @Test
    public void should_get_schedule_by_id() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(1L);
        when(scheduleService.findSchedule(any())).thenReturn(scheduleDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/schedule/get")
                .accept(MEDIA_TYPE_JSON)
                .contentType(MEDIA_TYPE_JSON)
                .param("scheduleId", "1" ))
                .andExpect(status().isOk());

    }


    @Test
    public void should_get_schedule_by_dates() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");

        List<ScheduleDTO> listScheduleDTO = new ArrayList<>();
        when(scheduleService.findSchedulesByDates(any(),any())).thenReturn(listScheduleDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/schedule/get-by-dates")
                .accept(MEDIA_TYPE_JSON)
                .contentType(MEDIA_TYPE_JSON)
                .param("startDate", "2021-07-12T11:17" )
                .param("endDate", "2021-07-12T12:17" ))
                .andExpect(status().isOk());

    }


}

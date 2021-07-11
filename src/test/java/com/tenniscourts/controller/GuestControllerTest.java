package com.tenniscourts.controller;

import com.tenniscourts.config.AbstractTest;
import com.tenniscourts.config.WebTestConfig;
import com.tenniscourts.dto.GuestDTO;
import com.tenniscourts.dto.ScheduleDTO;
import com.tenniscourts.service.*;
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
public class GuestControllerTest extends AbstractTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private TennisCourtService tennisCourtService;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private ScheduleService scheduleService;

    @MockBean
    private AdminService adminService;


    @MockBean
    private GuestService guestService;

    @Test
    public void should_add_guest() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");

        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setId(1L);
        when(guestService.create(any())).thenReturn(guestDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/guests/add")
                .accept(MEDIA_TYPE_JSON)
                .contentType(MEDIA_TYPE_JSON)
                .content(createScheduleRequestJson()))
                .andExpect(status().isCreated());

    }


    @Test
    public void should_get_guest_by_id() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");

        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setId(1L);
        when(guestService.findById(any())).thenReturn(guestDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/guests/get")
                .accept(MEDIA_TYPE_JSON)
                .contentType(MEDIA_TYPE_JSON)
                .param("guestId", "1" ))
                .andExpect(status().isOk());

    }

    @Test
    public void should_get_guest_by_name() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");

        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setId(1L);
        when(guestService.findById(any())).thenReturn(guestDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/guests/get-by-name")
                .accept(MEDIA_TYPE_JSON)
                .contentType(MEDIA_TYPE_JSON)
                .param("name", "Rogerer Federer" ))
                .andExpect(status().isOk());

    }

    @Test
    public void should_cancel_guest_by_id() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");

        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setId(1L);
        when(guestService.cancel(any())).thenReturn(guestDTO);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/guests/delete")
                .accept(MEDIA_TYPE_JSON)
                .contentType(MEDIA_TYPE_JSON)
                .param("guestId", "1" ))
                .andExpect(status().isOk());

    }

    @Test
    public void should_modify_guest() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");

        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setId(1L);
        when(guestService.create(any())).thenReturn(guestDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/guests/modify")
                .accept(MEDIA_TYPE_JSON)
                .contentType(MEDIA_TYPE_JSON)
                .content(createScheduleRequestJson()))
                .andExpect(status().isOk());


    }



}

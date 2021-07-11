package com.tenniscourts.controller;

import com.tenniscourts.config.AbstractTest;
import com.tenniscourts.config.WebTestConfig;
import com.tenniscourts.dto.ReservationDTO;
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
public class ReservationControllerTest extends AbstractTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private TennisCourtService tennisCourtService;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private ScheduleService scheduleService;

    @MockBean
    private GuestService guestService;

    @MockBean
    private AdminService adminService;



    @Test
    public void should_create_reservation() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(1L);
        when(reservationService.bookReservation(any())).thenReturn(reservationDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservation/add")
                .accept(MEDIA_TYPE_JSON)
                .contentType(MEDIA_TYPE_JSON)
                .content(createReservationRequestJson()))
                .andExpect(status().isCreated());

    }

    @Test
    public void should_find_reservation_by_id() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(1L);
        when(reservationService.findReservation(any())).thenReturn(reservationDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/reservation/get")
                .accept(MEDIA_TYPE_JSON)
                .contentType(MEDIA_TYPE_JSON)
                .param("reservationId", "1"))
                .andExpect(status().isOk());

    }


    @Test
    public void should_delete_reservation_by_id() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(1L);
        when(reservationService.cancelReservation(any())).thenReturn(reservationDTO);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/reservation/delete")
                .accept(MEDIA_TYPE_JSON)
                .contentType(MEDIA_TYPE_JSON)
                .param("reservationId", "1"))
                .andExpect(status().isOk());

    }

    @Test
    public void should_modify_reservation() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MediaType MEDIA_TYPE_JSON = new MediaType("application", "json");

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(1L);
        when(reservationService.cancelReservation(any())).thenReturn(reservationDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/reservation/modify")
                .accept(MEDIA_TYPE_JSON)
                .contentType(MEDIA_TYPE_JSON)
                .param("reservationId", "1")
                .param("scheduleId", "1"))
                .andExpect(status().isOk());

    }

}

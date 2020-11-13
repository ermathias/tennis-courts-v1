package com.tenniscourts.reservations;

import com.tenniscourts.tenniscourts.*;
import com.tenniscourts.schedules.*;
import com.tenniscourts.guests.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private MockMvc mockMvcTennisCourt;

        @Autowired
        private MockMvc mockMvcSchedule;

        @Autowired
        private MockMvc mockMvcGuest;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private ReservationController reservationController;

        @Autowired
        private ScheduleController scheduleController;

        @Autowired
        private TennisCourtController tennisCourtController;

        @Autowired
        private GuestController guestController;

        @Before
        public void setUp() {
                this.mockMvcTennisCourt = MockMvcBuilders.standaloneSetup(tennisCourtController).build();
                this.mockMvcSchedule = MockMvcBuilders.standaloneSetup(scheduleController).build();
                this.mockMvcGuest = MockMvcBuilders.standaloneSetup(guestController).build();
                this.mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
        }

        @Test
        public void testGETIndexReservation() throws Exception {
                this.mockMvc.perform(MockMvcRequestBuilders.get("/reservations/all"))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void testPOSTIndexReservationsMustNotReceivePostWithoutParameters() throws Exception {
                this.mockMvc.perform(MockMvcRequestBuilders.post("/reservations"))
                                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void shouldCreateAReservation() throws Exception {
                TennisCourtDTO tennisCourtDTO = TennisCourtDTO.builder().name("A name for tennis court")
                                .tennisCourtSchedules(new ArrayList<>()).build();
                mockMvcTennisCourt.perform(post("/tennis-courts").contentType("application/json")
                                .content(objectMapper.writeValueAsString(tennisCourtDTO)));

                CreateScheduleRequestDTO createScheduleRequestDTO = CreateScheduleRequestDTO.builder()
                                .tennisCourtId(Long.valueOf(1)).startDateTime(LocalDateTime.now()).build();

                mockMvcSchedule.perform(post("/schedules").contentType("application/json")
                                .content(objectMapper.writeValueAsString(createScheduleRequestDTO)));

                GuestDTO guestDTO = GuestDTO.builder().name("A name for tennis court").build();

                mockMvcGuest.perform(post("/guests").contentType("application/json")
                                .content(objectMapper.writeValueAsString(guestDTO)));

                CreateReservationRequestDTO createReservationRequestDTO = CreateReservationRequestDTO.builder()
                                .guestId(Long.valueOf(1)).scheduleId(Long.valueOf(1)).build();

                mockMvc.perform(post("/reservations").contentType("application/json")
                                .content(objectMapper.writeValueAsString(createReservationRequestDTO)))
                                .andExpect(status().isCreated());
        }

}
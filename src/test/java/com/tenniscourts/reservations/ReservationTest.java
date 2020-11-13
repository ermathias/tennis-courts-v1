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
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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

                CreateReservationRequestDTO createReservationRequestDTO = CreateReservationRequestDTO.builder()
                                .guestId(Long.valueOf(1)).scheduleId(Long.valueOf(1)).build();

                mockMvc.perform(post("/reservations").contentType("application/json")
                                .content(objectMapper.writeValueAsString(createReservationRequestDTO)))
                                .andExpect(status().isCreated());
        }

        //    1. As a User I want to be able to book a reservation for one or more tennis court at a given date schedule

        @Test
        void shouldCreateMultipleAReservation() throws Exception {

                // CREATE PREMIERE SCHEDULE
                CreateReservationRequestDTO createReservationRequestDTO1 = CreateReservationRequestDTO.builder()
                        .guestId(Long.valueOf(1)).scheduleId(Long.valueOf(1)).build();

                mockMvc.perform(post("/reservations").contentType("application/json")
                        .content(objectMapper.writeValueAsString(createReservationRequestDTO1)))
                        .andExpect(status().isCreated());

                // CREATE SECOND SCHEDULE
                CreateReservationRequestDTO createReservationRequestDTO2 = CreateReservationRequestDTO.builder()
                        .guestId(Long.valueOf(1)).scheduleId(Long.valueOf(2)).build();

                mockMvc.perform(post("/reservations").contentType("application/json")
                        .content(objectMapper.writeValueAsString(createReservationRequestDTO2)))
                        .andExpect(status().isCreated());
        }


        //    3. As a User I want to be able to cancel a reservation

        @Test
        void shouldRescheduleAReservation() throws Exception {

                // RESCHEDULE
                this.mockMvc.perform(MockMvcRequestBuilders.patch("/reservations/1/reschedule/2"))
                        .andExpect(MockMvcResultMatchers.status().isOk());
        }



        //    2. As a User I want to be able to see what time slots are free





        //    4. As a User I want to be able to reschedule a reservation


}
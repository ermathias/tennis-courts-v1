package com.tenniscourts.schedules;

import com.tenniscourts.tenniscourts.*;

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

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SchedulesTest {

        static final private String DATE_FORMAT = "yyyy-MM-dd";

        public static LocalDate string2LocalDate(final String date) {
                return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
        }

        @Autowired
        private MockMvc mockMvcTennisCourt;

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private ScheduleController scheduleController;

        @Autowired
        private TennisCourtController tennisCourtController;

        @Before
        public void setUp() {
                this.mockMvcTennisCourt = MockMvcBuilders.standaloneSetup(tennisCourtController).build();
                this.mockMvc = MockMvcBuilders.standaloneSetup(scheduleController).build();
        }

        @Test
        public void testGETIndexSchedulesWithAll() throws Exception {
                this.mockMvc.perform(MockMvcRequestBuilders.get("/schedules/all"))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void testGETIndexSchedulesWithNext() throws Exception {
                this.mockMvc.perform(MockMvcRequestBuilders.get("/schedules/next"))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void testPOSTIndexSchedulesMustNotReceivePostWithoutParameters() throws Exception {
                this.mockMvc.perform(MockMvcRequestBuilders.post("/schedules"))
                                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void shouldCreateASchedule() throws Exception {

                CreateScheduleRequestDTO createScheduleRequestDTO = CreateScheduleRequestDTO.builder()
                                .tennisCourtId(Long.valueOf(1)).startDateTime(LocalDateTime.now()).build();

                mockMvc.perform(post("/schedules").contentType("application/json")
                                .content(objectMapper.writeValueAsString(createScheduleRequestDTO)))
                                .andExpect(status().isCreated());
        }

        @Test
        public void testGETIndexSchedulesForId() throws Exception {

                this.mockMvc.perform(MockMvcRequestBuilders.get("/schedules/1"))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

}
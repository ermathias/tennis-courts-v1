package com.tenniscourts.schedules;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenniscourts.tenniscourts.TennisCourtController;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SchedulesTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MockMvc mockMvcTennisCourt;
    @Autowired
    private ScheduleController scheduleController;
    @Autowired
    private TennisCourtController tennisCourtController;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(scheduleController).build();
        mockMvcTennisCourt = MockMvcBuilders.standaloneSetup(tennisCourtController).build();
    }

    @Test
    public void testGETIndexSchedulesWithAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/schedules/all"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGETIndexSchedulesForId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/schedules/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGETIndexSchedulesWithNext() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/schedules/next"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testPOSTIndexSchedulesMustNotReceivePostWithoutParameters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/schedules"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void shouldCreateASchedule() throws Exception {
        CreateScheduleRequestDTO createScheduleRequestDTO = CreateScheduleRequestDTO.builder()
                .tennisCourtId(1L)
                .startDateTime(LocalDateTime.now()).build();

        mockMvc.perform(post("/schedules").contentType("application/json")
                .content(objectMapper.writeValueAsString(createScheduleRequestDTO)))
                .andExpect(status().isCreated());
    }

}
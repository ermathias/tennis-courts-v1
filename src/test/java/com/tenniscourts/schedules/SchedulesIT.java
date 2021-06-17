package com.tenniscourts.schedules;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class SchedulesIT {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreateNewSchedule() throws Exception {
        CreateScheduleRequestDTO request = new CreateScheduleRequestDTO(1L, LocalDateTime.now());

        this.mockMvc.perform(post("/schedules").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(emptyString()));
    }

    @Test
    public void shouldNotAllowMultipleSchedulesOnSameCourt() throws Exception {
        this.mockMvc.perform(post("/schedules").contentType(MediaType.APPLICATION_JSON).content("{\"tennisCourtId\":1,\"startDateTime\":\"2021-06-17T11:00\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(emptyString()));

        this.mockMvc.perform(post("/schedules").contentType(MediaType.APPLICATION_JSON).content("{\"tennisCourtId\":1,\"startDateTime\":\"2021-06-17T11:00\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Tennis Court is already scheduled for datetime")));
    }

}

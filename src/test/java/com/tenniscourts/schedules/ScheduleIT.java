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
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ScheduleIT {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldHaveTwo2020Schedules() throws Exception {
        mockMvc.perform(get("/schedules")
                /**/.param("startDate", LocalDate.of(2020, Month.JANUARY, 1).format(DateTimeFormatter.ISO_DATE))
                /**/.param("endDate", LocalDate.of(2020, Month.DECEMBER, 31).format(DateTimeFormatter.ISO_DATE)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1},{\"id\":2}]"));
    }

    @Test
    public void shouldHaveOne2030ScheduleEvailable() throws Exception {
        mockMvc.perform(get("/schedules")
                /**/.param("all-available", "true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":4}]"));
    }

    @Test
    public void shouldSaveAndGetNewGuest() throws Exception {
        LocalDateTime start = LocalDateTime.now().plusHours(1).truncatedTo(ChronoUnit.MINUTES);
        CreateScheduleRequestDTO dto = new CreateScheduleRequestDTO();
        dto.setStartDateTime(start);
        dto.setTennisCourtId(1L);
        MvcResult result = mockMvc.perform(post("/schedules").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(get(Objects.requireNonNull(result.getResponse().getHeader("Location"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"tennisCourt\":{\"id\":1},\"startDateTime\":\"" + start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")) + "\"}"));
    }

}

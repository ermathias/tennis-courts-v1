package com.tenniscourts.tenniscourts;

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
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TennisCourtIT {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldHaveTennisCourtWithSchedules() throws Exception {
        mockMvc.perform(get("/tennis-courts/1").param("includeSchedules", "true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Roland Garros - Court Philippe-Chatrier\"," +
                        "\"tennisCourtSchedules\":[{},{},{},{}]}"));
    }

    @Test
    public void shouldSaveAndGetNewGuest() throws Exception {
        TennisCourtDTO dto = new TennisCourtDTO();
        dto.setName("New Court");
        MvcResult result = mockMvc.perform(post("/tennis-courts").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(get(Objects.requireNonNull(result.getResponse().getHeader("Location"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"New Court\"}"));
    }

}

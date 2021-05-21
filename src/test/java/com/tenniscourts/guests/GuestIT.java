package com.tenniscourts.guests;

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
public class GuestIT {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldHaveTwoGuests() throws Exception {
        mockMvc.perform(get("/guests"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Roger Federer\"},{\"id\":2,\"name\":\"Rafael Nadal\"}]"));
    }

    @Test
    public void shouldFindOneRoger() throws Exception {
        mockMvc.perform(get("/guests").param("name", "roger"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Roger Federer\"}]"));
    }

    @Test
    public void shouldSaveAndGetNewGuest() throws Exception {
        CreateGuestRequestDTO dto = new CreateGuestRequestDTO();
        dto.setName("Henrique Philippi");

        MvcResult result = mockMvc.perform(post("/guests").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(get(Objects.requireNonNull(result.getResponse().getHeader("Location"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"Henrique Philippi\"}"));
    }

    @Test
    public void shouldDeleteGuest() throws Exception {
        mockMvc.perform(delete("/guests/2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateGuest() throws Exception {
        UpdateGuestRequestDTO dto = new UpdateGuestRequestDTO();
        dto.setName("Henrique Philippi");
        mockMvc.perform(put("/guests/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Henrique Philippi\"}"));
    }
}

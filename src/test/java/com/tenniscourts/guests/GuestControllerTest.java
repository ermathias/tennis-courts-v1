package com.tenniscourts.guests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GuestControllerTest {

    @Autowired
    MockMvc mvc;

    @Mock
    GuestService guestService;

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getAllGuestsAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/guests")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").isNotEmpty());
    }

    @Test
    public void getGuestByIdAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/guest/{guestId}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));

    }

    @Test
    public void createGuestAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/guest").content(asJsonString(new GuestDTO(3L, "Yolo"))).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteGuestAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/guest/{guestId}", 1))
                .andExpect(status().isOk());

    }

    @Test
    public void updateGuestAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .put("/guest").content(asJsonString(new GuestDTO(1L, "Working"))).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Working"));
    }

    @Test
    public void getGuestByNameAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/guest?guestName=Rafael Nadal")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2));
    }
}

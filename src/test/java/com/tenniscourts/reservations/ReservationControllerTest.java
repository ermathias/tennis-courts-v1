package com.tenniscourts.reservations;

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
public class ReservationControllerTest {

    @Autowired
    MockMvc mvc;

    @Mock
    ReservationController reservationController;

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getAllReservationsAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/reservations")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].idGuest").isNotEmpty());
    }

    @Test
    public void getReservationByIdAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/reservation/{reservationId}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));

    }

    @Test
    public void bookReservationAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/reservation").content(asJsonString(new CreateReservationRequestDTO(1L, 1L))).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void cancelReservationAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/reservation/cancel/{reservationId}", 1))
                .andExpect(status().isOk());

    }

    @Test
    public void updateGuestAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/reservation/{reservationId}/schedule/{scheduleId}", 1, 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Working"));
    }
}

package com.tenniscourts.reservations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
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
public class ReservationIT {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldHaveTwoReservations() throws Exception {
        mockMvc.perform(get("/reservations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"guest\":{\"id\":1},\"schedule\":{\"id\":1},\"reservationStatus\":\"READY_TO_PLAY\"}," +
                        "{\"guest\":{\"id\":1},\"schedule\":{\"id\":3},\"reservationStatus\":\"READY_TO_PLAY\"}]"));
    }

    @Test
    public void shouldHaveOnePastReservation() throws Exception {
        mockMvc.perform(get("/reservations").param("past", "true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"guest\":{\"id\":1},\"schedule\":{\"id\":1},\"reservationStatus\":\"READY_TO_PLAY\"}]"));
    }

    @Test
    public void shouldSaveAndGetNewReservation() throws Exception {
        CreateReservationRequestDTO dto = new CreateReservationRequestDTO();
        dto.setGuestId(2L);
        dto.setScheduleId(2L);

        MvcResult result = mockMvc.perform(post("/reservations").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(get(Objects.requireNonNull(result.getResponse().getHeader("Location"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"schedule\":{\"id\":2},\"guest\":{\"id\":2}}"));
    }

    @Test
    public void shouldNotBookReservedSchedule() throws Exception {
        CreateReservationRequestDTO dto = new CreateReservationRequestDTO();
        dto.setGuestId(2L);
        dto.setScheduleId(1L);

        mockMvc.perform(post("/reservations").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().json("{\"message\":\"Cannot book an already reserved schedule.\"}"));
    }

    @Test
    public void shouldCancelReservation() throws Exception {
        mockMvc.perform(delete("/reservations/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"reservationStatus\":\"CANCELLED\",\"value\":0,\"refundValue\":10}"));
    }

    @Test
    public void shouldRescheduleReservation() throws Exception {
        ReservationRescheduleRequestDTO dto = new ReservationRescheduleRequestDTO();
        dto.setScheduleId(4L);
        mockMvc.perform(post("/reservations/2/reschedule").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"reservationStatus\":\"READY_TO_PLAY\",\"previousReservation\":{\"reservationStatus\":\"RESCHEDULED\"}}"));
    }

    @Test
    public void shouldFailRescheduleSameSlot() throws Exception {
        ReservationRescheduleRequestDTO dto = new ReservationRescheduleRequestDTO();
        dto.setScheduleId(1L);
        mockMvc.perform(post("/reservations/1/reschedule").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().json("{\"message\":\"Cannot reschedule to the same slot.\"}"));
    }

    @Test
    public void shouldFailReschedulePastDate() throws Exception {
        ReservationRescheduleRequestDTO dto = new ReservationRescheduleRequestDTO();
        dto.setScheduleId(4L);
        mockMvc.perform(post("/reservations/1/reschedule").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().json("{\"message\":\"Can cancel/reschedule only future dates.\"}"));
    }

}

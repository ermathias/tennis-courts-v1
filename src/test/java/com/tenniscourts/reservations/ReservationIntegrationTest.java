package com.tenniscourts.reservations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenniscourts.UriConstants;
import com.tenniscourts.schedules.ScheduleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReservationIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationController reservationController;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp()
    {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.reservationController).build();
    }


    @Test
    @DisplayName("As a User I want to be able to book a reservation for one or more tennis court at a given date schedule")
    public void bookOneReservationTest() throws Exception
    {
        // Given

        CreateReservationRequestDTO createReservationRequestDTO = CreateReservationRequestDTO.builder()
                .guestId(1L).scheduleId(1L).build();

        // When

        mockMvc.perform(RestDocumentationRequestBuilders.post(UriConstants.RESERVATION_PATH + UriConstants.BOOK_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(createReservationRequestDTO)))
                // Then

                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("As a User I want to be able to book a reservation for one or more tennis court at a given date schedule")
    public void bookTwoReservationsTest() throws Exception
    {
        // Given

        CreateReservationRequestDTO firstCreateReservationRequestDTO = CreateReservationRequestDTO.builder()
                .guestId(1L).scheduleId(1L).build();

        CreateReservationRequestDTO secondCreateReservationRequestDTO = CreateReservationRequestDTO.builder()
                .guestId(1L).scheduleId(2L).build();

        // When

        mockMvc.perform(post(UriConstants.RESERVATION_PATH + UriConstants.BOOK_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(firstCreateReservationRequestDTO)))
                // Then
                .andExpect(status().isCreated());

        mockMvc.perform(post(UriConstants.RESERVATION_PATH + UriConstants.BOOK_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(secondCreateReservationRequestDTO)))
                // Then
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("As a User I want to be able to cancel a reservation")
    public void cancelReservationTest() throws Exception
    {
        // Given
        CreateReservationRequestDTO firstCreateReservationRequestDTO = CreateReservationRequestDTO.builder()
                .guestId(1L).scheduleId(1L).build();

        mockMvc.perform(post(UriConstants.RESERVATION_PATH + UriConstants.BOOK_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(firstCreateReservationRequestDTO)));

        // When
        mockMvc.perform(delete(UriConstants.RESERVATION_PATH + "/" + 1L))
                // Then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("As a User I want to be able to reschedule a reservation")
    public void rescheduleReservationTest() throws Exception {
        // Given
        CreateReservationRequestDTO firstCreateReservationRequestDTO = CreateReservationRequestDTO.builder()
                .guestId(1L).scheduleId(1L).build();

        mockMvc.perform(post(UriConstants.RESERVATION_PATH + UriConstants.BOOK_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(firstCreateReservationRequestDTO)));

        // When
        mockMvc.perform(put(UriConstants.RESERVATION_PATH + UriConstants.RESCHEDULE_PATH + "/" + 1L + "/" + 2L))
                // Then
                .andExpect(status().isOk());
    }
}
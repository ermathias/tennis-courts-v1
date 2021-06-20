package com.tenniscourts.service;

import com.tenniscourts.Fixtures;
import com.tenniscourts.model.Reservation;
import com.tenniscourts.storage.ReservationDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.BDDMockito.given;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ReservationService.class)
public class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    @Test
    @DisplayName("Test verify the method of service ReservationServiceTest - BookReservation -")
    public void verifyBookReservationTest() {
        ReservationDTO reservationDTO = Fixtures.buildReservationDTO();
        given(reservationService.bookReservation(Fixtures.buildCreateReservationRequestDTO())).willReturn(reservationDTO);

        ReservationDTO reservationDTO1 = reservationService.bookReservation(Fixtures.buildCreateReservationRequestDTO());
        Assert.assertEquals(reservationDTO1.getReservationStatus(),reservationDTO.getReservationStatus());
        Assert.assertEquals(reservationDTO1.getGuestId(),reservationDTO.getGuestId());
        Assert.assertEquals(reservationDTO1.getId(),reservationDTO.getId());
    }

    @Test
    @DisplayName("Test verify the method of service ReservationServiceTest - BookReservations -")
    public void verifyBookReservationsTest() {
        boolean aBoolean = true;
        given(reservationService.bookReservations(List.of(Fixtures.buildCreateReservationRequestDTO()))).willReturn(aBoolean);

        boolean aBoolean1 = reservationService.bookReservations(List.of(Fixtures.buildCreateReservationRequestDTO()));
        Assert.assertEquals(aBoolean1, aBoolean);
    }

    @Test
    @DisplayName("Test verify the method of service ReservationServiceTest - FindReservation -")
    public void verifyFindReservationTest() {
        ReservationDTO reservationDTO = Fixtures.buildReservationDTO();
        given(reservationService.findReservation(1L)).willReturn(reservationDTO);

        ReservationDTO reservationDTO1 = reservationService.bookReservation(Fixtures.buildCreateReservationRequestDTO());
        Assert.assertEquals(reservationDTO1.getReservationStatus(),reservationDTO.getReservationStatus());
        Assert.assertEquals(reservationDTO1.getGuestId(),reservationDTO.getGuestId());
        Assert.assertEquals(reservationDTO1.getId(),reservationDTO.getId());
    }

    @Test
    @DisplayName("Test verify the method of service ReservationServiceTest - CancelReservation -")
    public void verifyCancelReservationTest() {
        ReservationDTO reservationDTO = Fixtures.buildReservationDTO();
        given(reservationService.cancelReservation(1L)).willReturn(reservationDTO);

        ReservationDTO reservationDTO1 = reservationService.bookReservation(Fixtures.buildCreateReservationRequestDTO());
        Assert.assertEquals(reservationDTO1.getReservationStatus(),reservationDTO.getReservationStatus());
        Assert.assertEquals(reservationDTO1.getGuestId(),reservationDTO.getGuestId());
        Assert.assertEquals(reservationDTO1.getId(),reservationDTO.getId());
    }

    @Test
    @DisplayName("Test verify the method of service ReservationServiceTest - CancelReservation -")
    public void verifyCancelTest() {
        Reservation reservation = Fixtures.buildReservation();
        BigDecimal refundValue = Fixtures.buildReservation().getRefundValue();
        given(reservationService.getRefundValue(reservation)).willReturn(Fixtures.buildReservation().getRefundValue());

        BigDecimal refundValue1 = reservationService.getRefundValue(Fixtures.buildReservation());
        Assert.assertEquals(refundValue1, refundValue);
    }

    @Test
    @DisplayName("Test verify the method of service ReservationServiceTest - CancelReservation -")
    public void verifyRescheduleReservationTest() {
        ReservationDTO reservationDTO = Fixtures.buildReservationDTO();
        given(reservationService.rescheduleReservation(1l, 1L)).willReturn(reservationDTO);

        ReservationDTO reservationDTO1 = reservationService.rescheduleReservation(1L, 1L);
        Assert.assertEquals(reservationDTO1.getReservationStatus(),reservationDTO.getReservationStatus());
        Assert.assertEquals(reservationDTO1.getGuestId(),reservationDTO.getGuestId());
        Assert.assertEquals(reservationDTO1.getId(),reservationDTO.getId());
    }
}

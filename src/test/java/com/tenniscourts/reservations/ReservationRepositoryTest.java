package com.tenniscourts.reservations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import util.ModelGenerator;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;
    private ModelGenerator modelGenerator = new ModelGenerator();

    @Test
    public void saveShouldPersistReservationWhenSuccessful() {
        final Reservation reservationTobeSaved = modelGenerator.createReservationToSaveWithGuestIdAndScheduleId1();
        final Reservation savedReservation = reservationRepository.saveAndFlush(reservationTobeSaved);

        assertThat(savedReservation).isNotNull();
        assertThat(savedReservation.getId()).isNotNull();
        assertThat(savedReservation.getGuest()).isEqualTo(reservationTobeSaved.getGuest());
        assertThat(savedReservation.getSchedule()).isEqualTo(reservationTobeSaved.getSchedule());
        assertThat(savedReservation.getValue()).isEqualTo(reservationTobeSaved.getValue());
        assertThat(savedReservation.getReservationStatus()).isEqualTo(reservationTobeSaved.getReservationStatus());
        assertThat(savedReservation.getRefundValue()).isEqualTo(reservationTobeSaved.getRefundValue());
    }

    @Test
    public void saveShouldThrowConstraintViolationExceptionWhenValueIsNull() {
        final Reservation reservationTobeSaved = modelGenerator.createReservationToSaveWithGuestIdAndScheduleId1();
        reservationTobeSaved.setValue(null);

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> {
            reservationRepository.saveAndFlush(reservationTobeSaved);
        }).withMessageContaining("Value cannot be null");
    }

    @Test
    public void saveShouldThrowConstraintViolationExceptionWhenScheduleIsNull() {
        final Reservation reservationTobeSaved = modelGenerator.createReservationToSaveWithGuestIdAndScheduleId1();
        reservationTobeSaved.setSchedule(null);

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> {
            reservationRepository.saveAndFlush(reservationTobeSaved);
        }).withMessageContaining("Value cannot be null");
    }

    @Test
    public void findBySchedule_IdShouldReturnReservationListByScheduleIdWhenSuccessful() {
        reservationRepository.saveAndFlush(modelGenerator.createReservationToSaveWithGuestIdAndScheduleId1());
        final List<Reservation> reservationList = reservationRepository.findBySchedule_Id(1L);

        assertThat(reservationList).isNotNull().hasSize(1);
    }

    @Test
    public void findBySchedule_IdShouldReturnEmptyReservationListWhenScheduleIdIsNotFound() {
        final List<Reservation> reservationList = reservationRepository.findBySchedule_Id(2L);

        assertThat(reservationList).isNotNull().isEmpty();
    }

}
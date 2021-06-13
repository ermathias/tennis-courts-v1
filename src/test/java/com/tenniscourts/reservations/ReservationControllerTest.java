package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.ErrorDetails;
import com.tenniscourts.schedules.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import util.ModelGenerator;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private TestRestTemplate testRestTemplate;
    private ModelGenerator modelGenerator = new ModelGenerator();

    @Test
    void bookReservationShouldReturnEmptyBodyWhenSuccessful() {
        ResponseEntity<Void> response = testRestTemplate.exchange("/api/v1/reservation?guestId=1&scheduleId=1&value=10",
                HttpMethod.PUT, new HttpEntity<>(""), new ParameterizedTypeReference<Void>() {
                });

        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNull();
    }

    @Test
    void bookReservationShouldReturnStatusCode201WhenSuccessful() {
        ResponseEntity<Void> response = testRestTemplate.exchange("/api/v1/reservation?guestId=1&scheduleId=1&value=10",
                HttpMethod.PUT, new HttpEntity<>(""), new ParameterizedTypeReference<Void>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void bookReservationShouldReturnStatusCode500WhenReservationIsNotAvailable() {
        reservationRepository.saveAndFlush(modelGenerator.createReservationToSaveWithGuestIdAndScheduleId1());

        ResponseEntity<Void> response = testRestTemplate.exchange("/api/v1/reservation?guestId=1&scheduleId=1&value=10",
                HttpMethod.PUT, new HttpEntity<>(""), new ParameterizedTypeReference<Void>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void findReservationShouldReturnReservationDTOByIdWhenSuccessful() {
        reservationRepository.saveAndFlush(modelGenerator.createReservationToSaveWithGuestIdAndScheduleId1());

        ReservationDTO returnedReservation = testRestTemplate.exchange("/api/v1/reservation/1",
                HttpMethod.GET, new HttpEntity<>(""), new ParameterizedTypeReference<ReservationDTO>() {
                }).getBody();

        assertThat(returnedReservation).isNotNull();
        assertThat(returnedReservation.getId()).isEqualTo(1L);
    }

    @Test
    void findReservationShouldReturnStatusCode200WhenSuccessful() {
        reservationRepository.saveAndFlush(modelGenerator.createReservationToSaveWithGuestIdAndScheduleId1());

        ResponseEntity<ReservationDTO> response = testRestTemplate.exchange("/api/v1/reservation/1",
                HttpMethod.GET, new HttpEntity<>(""), new ParameterizedTypeReference<ReservationDTO>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void findReservationShouldReturnStatusCode404WhenSuccessful() {
        ResponseEntity<ReservationDTO> response = testRestTemplate.exchange("/api/v1/reservation/1",
                HttpMethod.GET, new HttpEntity<>(""), new ParameterizedTypeReference<ReservationDTO>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void cancelReservationShouldReturnCanceledReservationDTOBdWhenSuccessful() {
        reservationRepository.saveAndFlush(modelGenerator.createReservationToSaveWithGuestIdAndScheduleId1());

        ReservationDTO returnedReservation = testRestTemplate.exchange("/api/v1/reservation/cancel/1",
                HttpMethod.POST, new HttpEntity<>(""), new ParameterizedTypeReference<ReservationDTO>() {
                }).getBody();

        assertThat(returnedReservation).isNotNull();
        assertThat(returnedReservation.getId()).isEqualTo(1L);
        assertThat(returnedReservation.getReservationStatus()).isEqualTo("CANCELLED");
    }

    @Test
    void cancelReservationShouldReturnStatusCode200WhenSuccessful() {
        reservationRepository.saveAndFlush(modelGenerator.createReservationToSaveWithGuestIdAndScheduleId1());

        ResponseEntity<ReservationDTO> response = testRestTemplate.exchange("/api/v1/reservation/cancel/1",
                HttpMethod.POST, new HttpEntity<>(""), new ParameterizedTypeReference<ReservationDTO>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void cancelReservationShouldReturnStatusCode404WhenSuccessful() {
        ResponseEntity<ReservationDTO> response = testRestTemplate.exchange("/api/v1/reservation/cancel/1",
                HttpMethod.POST, new HttpEntity<>(""), new ParameterizedTypeReference<ReservationDTO>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void rescheduleReservationShouldReturnRescheduledReservationDTOBdWhenSuccessful() {
        reservationRepository.saveAndFlush(modelGenerator.createReservationToSaveWithGuestIdAndScheduleId1());
        scheduleRepository.saveAndFlush(modelGenerator.createScheduleToBeSaved());

        ReservationDTO returnedReservation = testRestTemplate.exchange("/api/v1/reservation/reschedule/1/2",
                HttpMethod.POST, new HttpEntity<>(""), new ParameterizedTypeReference<ReservationDTO>() {
                }).getBody();

        assertThat(returnedReservation).isNotNull();
        assertThat(returnedReservation.getReservationStatus()).isEqualTo("READY_TO_PLAY");
        assertThat(returnedReservation.getPreviousReservation().getReservationStatus()).isEqualTo("RESCHEDULED");
    }

    @Test
    void rescheduleReservationShouldReturnErrorMessageWhenTryingToRescheduleToSameSlot() {
        reservationRepository.saveAndFlush(modelGenerator.createReservationToSaveWithGuestIdAndScheduleId1());

        ErrorDetails responseBody = testRestTemplate.exchange("/api/v1/reservation/reschedule/1/1",
                HttpMethod.POST, new HttpEntity<>(""), new ParameterizedTypeReference<ErrorDetails>() {
                }).getBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getMessage()).isEqualTo("Cannot reschedule to the same slot.");
    }

    @Test
    void rescheduleReservationShouldReturnErrorMessageWhenTryingToRescheduleNotReadyToPlayReservation() {
        Reservation reservation = modelGenerator.createReservationToSaveWithGuestIdAndScheduleId1();
        reservation.setReservationStatus(ReservationStatus.CANCELLED);
        reservationRepository.saveAndFlush(reservation);
        scheduleRepository.saveAndFlush(modelGenerator.createScheduleToBeSaved());

        ErrorDetails responseBody = testRestTemplate.exchange("/api/v1/reservation/reschedule/1/2",
                HttpMethod.POST, new HttpEntity<>(""), new ParameterizedTypeReference<ErrorDetails>() {
                }).getBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getMessage()).isEqualTo("Cannot cancel/reschedule because it's not in ready to play status.");
    }

    @Test
    void rescheduleReservationShouldReturnStatusCode200WhenSuccessful() {
        reservationRepository.saveAndFlush(modelGenerator.createReservationToSaveWithGuestIdAndScheduleId1());
        scheduleRepository.saveAndFlush(modelGenerator.createScheduleToBeSaved());

        ResponseEntity<ReservationDTO> returnedReservation = testRestTemplate.exchange("/api/v1/reservation/reschedule/1/2",
                HttpMethod.POST, new HttpEntity<>(""), new ParameterizedTypeReference<ReservationDTO>() {
                });

        assertThat(returnedReservation).isNotNull();
        assertThat(returnedReservation.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void rescheduleReservationShouldReturnStatusCode400WhenTryingToRescheduleToSameSlot() {
        reservationRepository.saveAndFlush(modelGenerator.createReservationToSaveWithGuestIdAndScheduleId1());

        ResponseEntity<ErrorDetails> response = testRestTemplate.exchange("/api/v1/reservation/reschedule/1/1",
                HttpMethod.POST, new HttpEntity<>(""), new ParameterizedTypeReference<ErrorDetails>() {
                });

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void rescheduleReservationShouldReturnStatusCode400WhenTryingToRescheduleNotReadyToPlayReservation() {
        Reservation reservation = modelGenerator.createReservationToSaveWithGuestIdAndScheduleId1();
        reservation.setReservationStatus(ReservationStatus.CANCELLED);
        reservationRepository.saveAndFlush(reservation);
        scheduleRepository.saveAndFlush(modelGenerator.createScheduleToBeSaved());

        ResponseEntity<ErrorDetails> response = testRestTemplate.exchange("/api/v1/reservation/reschedule/1/2",
                HttpMethod.POST, new HttpEntity<>(""), new ParameterizedTypeReference<ErrorDetails>() {
                });

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void rescheduleReservationShouldReturnStatusCode404WhenReservationIsNotFound() {
        ResponseEntity<ErrorDetails> response = testRestTemplate.exchange("/api/v1/reservation/reschedule/1/1",
                HttpMethod.POST, new HttpEntity<>(""), new ParameterizedTypeReference<ErrorDetails>() {
                });

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
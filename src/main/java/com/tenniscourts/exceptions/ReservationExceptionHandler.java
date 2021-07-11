package com.tenniscourts.exceptions;

import com.tenniscourts.model.ReservationError;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
@NoArgsConstructor
public class ReservationExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler(ReservationException.class)
    protected ResponseEntity<Object> handleReservationBusinessException(
           final RuntimeException runtimeException,
           final WebRequest request
           ){

        final ReservationError reservationError = new ReservationError(runtimeException.getMessage());

        final HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(runtimeException, reservationError, httpHeaders, HttpStatus.PRECONDITION_FAILED, request);

   }

}

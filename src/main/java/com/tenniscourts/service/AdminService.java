package com.tenniscourts.service;


import com.tenniscourts.dto.ReservationDTO;
import com.tenniscourts.exceptions.BusinessException;
import com.tenniscourts.model.Reservation;
import com.tenniscourts.model.ReservationStatus;
import com.tenniscourts.util.AdminPoliciesDateRefund;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {

    private final ReservationService reservationService;
    private final ScheduleService scheduleService;
    private final AdminPoliciesDateRefund adminPoliciesDateRefund;

    private int reservationDeposit;

    public AdminService(final ReservationService reservationService,
                        final ScheduleService scheduleService,
                        final AdminPoliciesDateRefund adminPoliciesDateRefund,
                        final @Value("${reservation.deposit}") int reservationDeposit){
        this.reservationService = reservationService;
        this.scheduleService = scheduleService;
        this.reservationDeposit = reservationDeposit;
        this.adminPoliciesDateRefund = adminPoliciesDateRefund;
    }

    public List<ReservationDTO> getHistory(){
        return reservationService.getHistoryReservation();
    }

    public void closeDailyReservations(){
        LocalDateTime closingTime= LocalDateTime.now();
        LocalDateTime openingTime= LocalDateTime.now().minusHours(12);

        if (closingTime.getHour() < 19){
            throw new BusinessException
                    ("Cannot run the daily job to close reservations before end of the day");
        }

       List<Reservation> dailyReservations = scheduleService.
               getDailyReservation(openingTime, closingTime);

       for (Reservation reservation : dailyReservations) {
           if (ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
               reservationService.updateReservation
                       (reservation,new BigDecimal(reservationDeposit),ReservationStatus.CLOSED);
           } else if (ReservationStatus.CANCELLED.equals(reservation.getReservationStatus())
           || ReservationStatus.RESCHEDULED.equals(reservation.getReservationStatus())){
              BigDecimal refundValue = applyRefundPolicies(reservation);
               reservationService.updateReservation
                       (reservation, refundValue, reservation.getReservationStatus());
           }

       }

    }

    private BigDecimal applyRefundPolicies(Reservation reservation) {

        LocalDateTime cancelledTime = reservation.getCancelledTime();

        if (cancelledTime == null){
            cancelledTime = reservation.getRescheduledTime();
        }

        LocalDateTime reservationTime = reservation.getSchedule().getStartDateTime();

        return adminPoliciesDateRefund.refundPolicy(cancelledTime, reservationTime);

    }


}

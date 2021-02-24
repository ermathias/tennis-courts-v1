package com.tenniscourts.reservations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.tenniscourts.schedules.Schedule;

import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {
    @Inject
	private EntityManager em;

    @Override
    public List<Reservation> getHistory(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);

        Root<Reservation> reservation = cq.from(Reservation.class);

        Join<Reservation, Schedule> reservationSchedule = reservation.join("schedule", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(cb.not(cb.equal(reservation.get("reservationStatus").as(ReservationStatus.class), ReservationStatus.READY_TO_PLAY)));

        if (startDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(reservationSchedule.<LocalDateTime>get("startDateTime").as(LocalDateTime.class), startDateTime));
        }

        if (endDate != null) {
            predicates.add(cb.lessThanOrEqualTo(reservationSchedule.<LocalDateTime>get("startDateTime").as(LocalDateTime.class), endDateTime));
        }

        cq.select(reservation).where(predicates.toArray(new Predicate[]{}));

        TypedQuery<Reservation> query = em.createQuery(cq);

        try {
            return query.getResultList();
        } catch (NoResultException nre) {
            return new ArrayList<Reservation>();
        }
    }
}

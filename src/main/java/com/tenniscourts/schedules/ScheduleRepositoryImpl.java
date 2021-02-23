package com.tenniscourts.schedules;

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

import com.tenniscourts.reservations.Reservation;
import com.tenniscourts.reservations.ReservationStatus;

import org.springframework.stereotype.Repository;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {

    @Inject
	private EntityManager em;

    @Override
    public List<Schedule> findSchedulesWithFreeTimeSlotsByScheduleDate(LocalDate scheduleDate) {
        LocalDateTime startDateTime = scheduleDate.atStartOfDay();
        LocalDateTime endDateTime = scheduleDate.atTime(LocalTime.MAX);

        CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<Schedule> cq = cb.createQuery(Schedule.class);
		
		Root<Schedule> schedule = cq.from(Schedule.class);
		
		Join<Schedule, Reservation> scheduleReservation = schedule.join("reservations", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(cb.not(cb.equal(scheduleReservation.get("reservationStatus").as(ReservationStatus.class), ReservationStatus.READY_TO_PLAY)));

        predicates.add(cb.between(schedule.get("startDateTime").as(LocalDateTime.class), startDateTime, endDateTime));

        cq.select(schedule).where(predicates.toArray(new Predicate[]{}));
		
		TypedQuery<Schedule> query = em.createQuery(cq);

        try {
			return query.getResultList();
		} catch (NoResultException nre) {
			return new ArrayList<Schedule>();
		}
    }   
}

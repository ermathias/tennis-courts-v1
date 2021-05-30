package com.tenniscourts.reservations;

import com.tenniscourts.schedules.ScheduleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class ReservationDTO {

    private Long id;

    private ScheduleDTO schedule;

    private String reservationStatus;

    private ReservationDTO previousReservation;

    private BigDecimal refundValue;

    private BigDecimal value;

    @NotNull
    private Long scheduledId;

    @NotNull
    private Long guestId;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the schedule
	 */
	public ScheduleDTO getSchedule() {
		return schedule;
	}

	/**
	 * @param schedule the schedule to set
	 */
	public void setSchedule(ScheduleDTO schedule) {
		this.schedule = schedule;
	}

	/**
	 * @return the reservationStatus
	 */
	public String getReservationStatus() {
		return reservationStatus;
	}

	/**
	 * @param reservationStatus the reservationStatus to set
	 */
	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	/**
	 * @return the previousReservation
	 */
	public ReservationDTO getPreviousReservation() {
		return previousReservation;
	}

	/**
	 * @param previousReservation the previousReservation to set
	 */
	public void setPreviousReservation(ReservationDTO previousReservation) {
		this.previousReservation = previousReservation;
	}

	/**
	 * @return the refundValue
	 */
	public BigDecimal getRefundValue() {
		return refundValue;
	}

	/**
	 * @param refundValue the refundValue to set
	 */
	public void setRefundValue(BigDecimal refundValue) {
		this.refundValue = refundValue;
	}

	/**
	 * @return the value
	 */
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	/**
	 * @return the scheduledId
	 */
	public Long getScheduledId() {
		return scheduledId;
	}

	/**
	 * @param scheduledId the scheduledId to set
	 */
	public void setScheduledId(Long scheduledId) {
		this.scheduledId = scheduledId;
	}

	/**
	 * @return the guestId
	 */
	public Long getGuestId() {
		return guestId;
	}

	/**
	 * @param guestId the guestId to set
	 */
	public void setGuestId(Long guestId) {
		this.guestId = guestId;
	}
}

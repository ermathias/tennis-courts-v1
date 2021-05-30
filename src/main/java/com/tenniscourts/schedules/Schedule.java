package com.tenniscourts.schedules;

import com.tenniscourts.config.persistence.BaseEntity;
import com.tenniscourts.reservations.Reservation;
import com.tenniscourts.tenniscourts.TennisCourt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = "reservations")
public class Schedule extends BaseEntity<Long> {

    @ManyToOne
    @NotNull
    private TennisCourt tennisCourt;

    @Column
    @NotNull
    private LocalDateTime startDateTime;

    @Column
    @NotNull
    private LocalDateTime endDateTime;

    @OneToMany
    private List<Reservation> reservations;

    public void addReservation(Reservation reservation) {
        if (this.reservations == null) {
            this.reservations = new ArrayList<>();
        }

        reservation.setSchedule(this);
        this.reservations.add(reservation);
    }

	/**
	 * @return the tennisCourt
	 */
	public TennisCourt getTennisCourt() {
		return tennisCourt;
	}

	/**
	 * @param tennisCourt the tennisCourt to set
	 */
	public void setTennisCourt(TennisCourt tennisCourt) {
		this.tennisCourt = tennisCourt;
	}

	/**
	 * @return the startDateTime
	 */
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	/**
	 * @param startDateTime the startDateTime to set
	 */
	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	/**
	 * @return the endDateTime
	 */
	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	/**
	 * @param endDateTime the endDateTime to set
	 */
	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	/**
	 * @return the reservations
	 */
	public List<Reservation> getReservations() {
		return reservations;
	}

	/**
	 * @param reservations the reservations to set
	 */
	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
}

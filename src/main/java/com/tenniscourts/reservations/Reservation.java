package com.tenniscourts.reservations;

import com.tenniscourts.config.persistence.BaseEntity;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.schedules.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Reservation extends BaseEntity<Long> {

    @ManyToOne
    private Guest guest;

    @ManyToOne
    @NotNull
    private Schedule schedule;

    @NotNull
    private BigDecimal value = BigDecimal.valueOf(10);

    @NotNull
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus = ReservationStatus.READY_TO_PLAY;

    private BigDecimal refundValue;

    public Reservation(Guest guest, Schedule schedule) {
        this.guest = guest;
        this.schedule = schedule;
    }

    public void update(ReservationStatus status) {
        Assert.isTrue(ReservationStatus.READY_TO_PLAY.equals(getReservationStatus()), "Cannot cancel/reschedule because it's not in ready to play status.");
        Assert.isTrue(getSchedule().getStartDateTime().isAfter(LocalDateTime.now()), "Can cancel/reschedule only future dates.");
        setReservationStatus(status);
        BigDecimal refundValue = calcRefundValue();
        setValue(getValue().subtract(refundValue));
        setRefundValue(refundValue);
    }

    public BigDecimal calcRefundValue() {
        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), getSchedule().getStartDateTime());
        if (hours >= 24) {
            return getValue();
        } else if (hours >= 12) {
            return getValue().multiply(new BigDecimal("0.75"));
        } else if (hours >= 2) {
            return getValue().multiply(new BigDecimal("0.50"));
        } else if (hours >= 0) {
            return getValue().multiply(new BigDecimal("0.25"));
        }
        return BigDecimal.ZERO;
    }
}

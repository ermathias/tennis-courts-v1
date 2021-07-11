package com.tenniscourts.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class AdminPoliciesDateRefund {

    public BigDecimal refundPolicy (
            LocalDateTime reservationTime, LocalDateTime cancelTime){

        BigDecimal refund = new BigDecimal(10);

        BigDecimal amountToKeep = new BigDecimal(0L);

        int hour = cancelTime.getHour();

        long hours = ChronoUnit.HOURS.between(cancelTime, reservationTime);

        hours = Math.abs(hours);

        if (hours > 24){
            refund = new BigDecimal(0);
        } else {
            if (hour <= 23 && hour >= 12){
                amountToKeep = percentage(refund, new BigDecimal(25));
            } else if (hour <= 11 && hour >= 2){
                amountToKeep = percentage(refund, new BigDecimal(50));
            } else if (hour <= 2 && hour >= 0){
                amountToKeep =  percentage(refund, new BigDecimal(75));
            }

        }

        return refund.subtract(amountToKeep);
    }


    private BigDecimal percentage(BigDecimal base, BigDecimal pct){
        return base.multiply(pct).divide(Consts.ONE_HUNDRED);
    }
}

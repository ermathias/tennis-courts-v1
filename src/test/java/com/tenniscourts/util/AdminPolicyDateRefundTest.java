package com.tenniscourts.util;

import com.tenniscourts.service.ReservationService;
import com.tenniscourts.service.ScheduleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ScheduleService.class)
public class AdminPolicyDateRefundTest {

    private AdminPoliciesDateRefund adminPoliciesDateRefund;

    @Before
    public void setup(){
        adminPoliciesDateRefund = new AdminPoliciesDateRefund();
    }

    @Test
    public void refundZeroPolicyTest (){

        LocalDateTime reservationTime = LocalDateTime.now().minusDays(1);

        LocalDateTime cancelTime = LocalDateTime.now().minusDays(3);

        BigDecimal refund = adminPoliciesDateRefund.refundPolicy(cancelTime, reservationTime);

        Assert.assertEquals(refund.longValue(), 0);
    }


    @Test
    public void refund25PolicyTest (){

        LocalDateTime reservationTime = LocalDateTime.of(2021, Month.JULY,11,15,30,40,50000);

        LocalDateTime cancelTime = LocalDateTime.now().minusDays(1);

        BigDecimal refund = adminPoliciesDateRefund.refundPolicy(cancelTime, reservationTime);

        Assert.assertEquals(refund.longValue(), 7);
    }

    @Test
    public void refund50PolicyTest (){

        LocalDateTime reservationTime = LocalDateTime.of(2021, Month.JULY,11,15,30,40,50000);

        LocalDateTime cancelTime = reservationTime.minusHours(12);

        BigDecimal refund = adminPoliciesDateRefund.refundPolicy(reservationTime, cancelTime);

        Assert.assertEquals(refund.longValue(), 5);
    }


    @Test
    public void refund75PolicyTest (){

        LocalDateTime reservationTime = LocalDateTime.of(2021, Month.JULY,11,15,30,40,50000);

        LocalDateTime cancelTime = reservationTime.minusHours(15);

        BigDecimal refund = adminPoliciesDateRefund.refundPolicy(reservationTime, cancelTime);

        Assert.assertEquals(refund.longValue(), 2);
    }

}

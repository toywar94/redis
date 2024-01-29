package com.toywar94.redis.service;

import com.toywar94.redis.dto.IssueCouponDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;

@SpringBootTest
public class RedisCouponIssueServiceTests {

    @Autowired
    private CouponIssueService couponIssueService;

    private static final int TOTAL_USERS = 1000;
    private static final String COUPON_CODE = "123456";

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void testIssueCouponConcurrently() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Exception[] exceptions = new Exception[1];

        Thread[] threads = new Thread[TOTAL_USERS];

        for (int i = 0; i < TOTAL_USERS; i++) {
            final int userId = i + 1;
            threads[i] = new Thread(() -> {
                try {
                    latch.await(); // Wait for the signal to start
                    System.out.println("******" + userId + "******");
                    couponIssueService.issueCoupon(new IssueCouponDTO(2L, userId, COUPON_CODE));
                } catch (Exception e) {
                    exceptions[0] = e;
                }
            });
            threads[i].start();
        }

        latch.countDown();

        for (Thread thread : threads) {
            thread.join();
        }

        if (exceptions[0] != null) {
            throw new RuntimeException("Exception occurred in one or more threads", exceptions[0]);
        }


    }

    @Test
    void issueCouponTest() {

        IssueCouponDTO couponDTO;
        for (int i = 0; i < 100; i++) {
            couponDTO = new IssueCouponDTO(
                    2L,
                    i + 1,
                    COUPON_CODE
            );
            couponIssueService.issueCoupon(couponDTO);
        }

    }
}

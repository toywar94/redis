package com.toywar94.redis.service;

import com.toywar94.redis.domain.Coupon;
import com.toywar94.redis.domain.UserCoupon;
import com.toywar94.redis.repository.CouponRepo;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final StringRedisTemplate redisTemplate;
    private final CouponRepo couponRepo;
    private static final String ISSUE_COUPON_KEY = "event_coupon";

    private void registerCouponToUser(Set<String> users) throws BadRequestException {
        Coupon coupon = couponRepo.findById(2L)
                .orElseThrow(() -> new BadRequestException("cannot find coupon"));

        List<UserCoupon> userCoupons = new ArrayList<>();
        users.forEach(user -> {
            userCoupons.add(UserCoupon.builder()
                    .coupon(coupon)
                    .userId(Long.parseLong(user))
                    .build());
        });

        coupon.addAll(userCoupons);
        couponRepo.save(coupon);
    }

    @Scheduled(cron = "0 0 18 1 3 2024")
    private void eventCouponScheduler() throws BadRequestException {

        final int limit = 10;
        ZSetOperations zSetOps = redisTemplate.opsForZSet();
        Set<String> getCoupon = zSetOps.range(ISSUE_COUPON_KEY, 0, limit - 1);

        registerCouponToUser(getCoupon);

    }
}

package com.toywar94.redis.service;

import com.toywar94.redis.dto.IssueCouponDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponIssueService {

    private final StringRedisTemplate redisTemplate;
    private static final String ISSUE_COUPON_KEY = "event_coupon";

    /* 요구 사항
    * 1. 중복 지급하지 않는다.
    * 2. 특정 시간(3/1)에 마감한다.
    * 3. 선착순 10명에게 발급한다.
    * */

    public boolean issueCoupon(IssueCouponDTO issueCoupon) {
        register(issueCoupon.getUserId());
        return true;
    }

    private void register(long userId){
        redisTemplate.opsForZSet()
                .addIfAbsent(
                        ISSUE_COUPON_KEY,
                        String.valueOf(userId),
                        System.currentTimeMillis()
                );
    }



}

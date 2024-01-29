package com.toywar94.redis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@AllArgsConstructor
public class IssueCouponDTO {
    private long id;
    private long userId;
    private String code;
}

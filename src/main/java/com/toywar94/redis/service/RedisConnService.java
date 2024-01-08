package com.toywar94.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisConnService {

    private final StringRedisTemplate redisTemplate;


    public void setValue(final String k, final String v){
        redisTemplate.opsForValue().set(k, v);
    }

    public String getValue(String k) {
        return redisTemplate.opsForValue().get(k);
    }

    public void deleteKey(String k){
        redisTemplate.delete(k);
    }
}

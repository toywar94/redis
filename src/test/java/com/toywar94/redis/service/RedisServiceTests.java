package com.toywar94.redis.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RedisServiceTests {

    @Autowired
    private RedisConnService redisConnService;

    @Test
    void redisTest(){
        final String k = "a";
        final String v = "test";
        redisConnService.setValue(k, v);

        String getValue = redisConnService.getValue(k);
        assertEquals(v, getValue);

        redisConnService.deleteKey(k);

        String deletedValue = redisConnService.getValue(k);

        assertNull(deletedValue);
    }
}

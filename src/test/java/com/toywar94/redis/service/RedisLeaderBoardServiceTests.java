package com.toywar94.redis.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RedisLeaderBoardServiceTests {

    @Autowired
    private RedisLeaderBoardService leaderBoardService;


    @BeforeAll
    void setup(){
        for (int i = 1; i <= 10000; i++) {
            leaderBoardService.setUserScore(String.valueOf(i), i);
        }
    }

    @Test
    @DisplayName("리더보드에 유저 데이터 등록")
    void setUserScoreTest(){
        boolean b = leaderBoardService.setUserScore(String.valueOf(100000), 100000);
        assertTrue(b);
    }

    @Test
    @DisplayName("리더보드 유저 검색")
    void getUserRank(){
        long userRank = leaderBoardService.getUserRank(String.valueOf(1));
        assertEquals(10000, 10000);
    }


    @Test
    @DisplayName("리더보드 limit 100명 데이터 가져오기")
    void getTopNRank(){
        Set<String> topNRank = leaderBoardService.getTopNRank(100);

        assertEquals(100, topNRank.size());
    }

}

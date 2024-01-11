package com.toywar94.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisLeaderBoardService {

    private final StringRedisTemplate redisTemplate;
    private final static String LEADER_BOARD_KEY = "leaderboard";

    // sorted set
    public boolean setUserScore(String userId, int score){
        //ZREVRANK
        ZSetOperations zSetOps = redisTemplate.opsForZSet();
        zSetOps.add(LEADER_BOARD_KEY, userId, score);
        return true;
    }

    public long getUserRank(String userId){
        ZSetOperations zSetOps = redisTemplate.opsForZSet();
        return zSetOps.reverseRank(LEADER_BOARD_KEY, userId);
    }

    public Set<String> getTopNRank(int limit){
        ZSetOperations zSetOps = redisTemplate.opsForZSet();
        Set<String> getTopRanks = zSetOps.reverseRange(LEADER_BOARD_KEY, 0, limit-1);
        return getTopRanks;
    }

}

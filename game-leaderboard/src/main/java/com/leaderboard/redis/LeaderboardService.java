package com.leaderboard.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeaderboardService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String LEADERBOARD_KEY = "game:leaderboard";

    // Add or update score in Redis Sorted Set
    public void updateLeaderboard(String username, double points) {
        redisTemplate.opsForZSet().incrementScore(LEADERBOARD_KEY, username, points);
        log.info("Leaderboard updated for {}", username);
    }

    // Top N players (highest score first)
    public Set<String> getTopPlayers(int topN) {
        return redisTemplate.opsForZSet().reverseRange(LEADERBOARD_KEY, 0, topN - 1);
    }

    // Get rank of a specific player
    public Long getPlayerRank(String username) {
        Long rank = redisTemplate.opsForZSet().reverseRank(LEADERBOARD_KEY, username);
        return rank != null ? rank + 1 : null;
    }

    // Get total score of a player
    public Double getPlayerScore(String username) {
        return redisTemplate.opsForZSet().score(LEADERBOARD_KEY, username);
    }

    // Reset leaderboard
    public void resetLeaderboard() {
        redisTemplate.delete(LEADERBOARD_KEY);
    }
}
package com.leaderboard.kafka;

import com.leaderboard.dto.ScoreEvent;
import com.leaderboard.redis.LeaderboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScoreEventConsumer {

    private final LeaderboardService leaderboardService;

    @KafkaListener(topics = "score-events", groupId = "leaderboard-group")
    public void consume(ScoreEvent event) {
        log.info("Consumed score event: {} scored {} in {}",
                event.getUsername(), event.getPoints(), event.getGameName());
        leaderboardService.updateLeaderboard(event.getUsername(), event.getPoints());
    }
}
package com.leaderboard.kafka;

import com.leaderboard.dto.ScoreEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScoreEventProducer {

    private final KafkaTemplate<String, ScoreEvent> kafkaTemplate;
    private static final String TOPIC = "score-events";

    public void sendScoreEvent(ScoreEvent event) {
        log.info("Publishing score event for player: {}", event.getUsername());
        kafkaTemplate.send(TOPIC, event.getUsername(), event);
    }
}
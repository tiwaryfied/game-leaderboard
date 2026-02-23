package com.leaderboard.service;

import com.leaderboard.dto.ScoreEvent;
import com.leaderboard.kafka.ScoreEventProducer;
import com.leaderboard.model.Player;
import com.leaderboard.model.Score;
import com.leaderboard.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final PlayerService playerService;
    private final ScoreEventProducer scoreEventProducer;

    public Score submitScore(Long playerId, double points, String gameName) {
        Player player = playerService.getPlayerById(playerId);

        Score score = Score.builder()
                .playerId(playerId)
                .username(player.getUsername())
                .points(points)
                .gameName(gameName)
                .createdAt(LocalDateTime.now())
                .build();

        Score saved = scoreRepository.save(score);

        // Fire Kafka event → Consumer will update Redis leaderboard
        ScoreEvent event = ScoreEvent.builder()
                .playerId(playerId)
                .username(player.getUsername())
                .points(points)
                .gameName(gameName)
                .build();

        scoreEventProducer.sendScoreEvent(event);
        return saved;
    }

    public List<Score> getScoresByPlayer(Long playerId) {
        return scoreRepository.findByPlayerId(playerId);
    }

    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    public void deleteScore(Long id) {
        scoreRepository.deleteById(id);
    }
}
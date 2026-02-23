package com.leaderboard.repository;

import com.leaderboard.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByPlayerId(Long playerId);
}
package com.leaderboard.controller;

import com.leaderboard.model.Score;
import com.leaderboard.redis.LeaderboardService;
import com.leaderboard.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/scores")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;
    private final LeaderboardService leaderboardService;

    @PostMapping("/submit")
    public ResponseEntity<Score> submitScore(@RequestParam Long playerId,
                                             @RequestParam double points,
                                             @RequestParam String gameName) {
        return ResponseEntity.ok(scoreService.submitScore(playerId, points, gameName));
    }

    @GetMapping
    public ResponseEntity<List<Score>> getAllScores() {
        return ResponseEntity.ok(scoreService.getAllScores());
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<Score>> getScoresByPlayer(@PathVariable Long playerId) {
        return ResponseEntity.ok(scoreService.getScoresByPlayer(playerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteScore(@PathVariable Long id) {
        scoreService.deleteScore(id);
        return ResponseEntity.ok("Score deleted");
    }

    // Redis Leaderboard Endpoints
    @GetMapping("/leaderboard/top/{n}")
    public ResponseEntity<Set<String>> getTopPlayers(@PathVariable int n) {
        return ResponseEntity.ok(leaderboardService.getTopPlayers(n));
    }

    @GetMapping("/leaderboard/rank/{username}")
    public ResponseEntity<Map<String, Object>> getPlayerRank(@PathVariable String username) {
        return ResponseEntity.ok(Map.of(
                "username", username,
                "rank", leaderboardService.getPlayerRank(username),
                "totalScore", leaderboardService.getPlayerScore(username)
        ));
    }

    @DeleteMapping("/leaderboard/reset")
    public ResponseEntity<String> resetLeaderboard() {
        leaderboardService.resetLeaderboard();
        return ResponseEntity.ok("Leaderboard reset");
    }
}
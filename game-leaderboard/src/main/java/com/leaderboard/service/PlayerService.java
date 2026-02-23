package com.leaderboard.service;

import com.leaderboard.model.Player;
import com.leaderboard.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + id));
    }

    public Player updatePlayer(Long id, Player updated) {
        Player existing = getPlayerById(id);
        existing.setUsername(updated.getUsername());
        existing.setEmail(updated.getEmail());
        return playerRepository.save(existing);
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}
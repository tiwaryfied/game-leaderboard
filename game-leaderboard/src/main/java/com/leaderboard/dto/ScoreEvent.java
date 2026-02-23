package com.leaderboard.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreEvent {
    private Long playerId;
    private String username;
    private double points;
    private String gameName;
}
package org.example.tournamentmaker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {
    private int id;
    private String name;
    private int played;
    private int wins;
    private int draws;
    private int losses;
    private int goalDifference;
    private int goalsScored;
    private int goalsConceded;
    private String last5Games;
    private int points;
}

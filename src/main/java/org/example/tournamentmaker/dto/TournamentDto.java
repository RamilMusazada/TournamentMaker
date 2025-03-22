package org.example.tournamentmaker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TournamentDto {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String type;
    private String status;
}

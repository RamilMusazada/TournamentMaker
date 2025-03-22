package org.example.tournamentmaker.impl;

import org.example.tournamentmaker.dto.TournamentDto;
import java.util.List;

public interface TournamentServiceImpl {
    TournamentDto createTournament(TournamentDto tournamentDto);
    TournamentDto getTournamentById(Long id);
    List<TournamentDto> getAllTournaments();
    TournamentDto updateTournament(Long id, TournamentDto tournamentDto);
    void deleteTournament(Long id);
}

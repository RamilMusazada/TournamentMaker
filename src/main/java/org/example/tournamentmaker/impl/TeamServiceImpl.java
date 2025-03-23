package org.example.tournamentmaker.impl;

import org.example.tournamentmaker.dto.TeamDto;

import java.util.List;

public interface TeamServiceImpl {
    TeamDto createTeam(TeamDto teamDto);

    TeamDto getTeamById(int id);

    List<TeamDto> getAllTeams();

    TeamDto updateTeam(int id, TeamDto teamDto);

    void deleteTeam(int id);

    List<TeamDto> getTeamsByTournamentId(Long tournamentId);

    void addTeamToTournament(int teamId, Long tournamentId);

    void removeTeamFromTournament(int teamId, Long tournamentId);

    void updateTeamStats(int teamId, int goalsScored, int goalsConceded, String result);
}

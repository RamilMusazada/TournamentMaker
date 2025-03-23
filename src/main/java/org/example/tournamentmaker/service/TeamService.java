package org.example.tournamentmaker.service;

import org.example.tournamentmaker.dto.TeamDto;
import org.example.tournamentmaker.entity.Team;
import org.example.tournamentmaker.exception.ResourceNotFoundException;
import org.example.tournamentmaker.impl.TeamRepositoryImpl;
import org.example.tournamentmaker.impl.TeamServiceImpl;
import org.example.tournamentmaker.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class TeamService implements TeamServiceImpl {
    private final TeamRepositoryImpl teamRepositoryImpl;

    public TeamService(TeamRepositoryImpl teamRepositoryImpl) {
        this.teamRepositoryImpl = teamRepositoryImpl;
    }

    private TeamDto convertToDTO(Team team) {
        return new TeamDto(
                team.getId(),
                team.getName(),
                team.getPlayed(),
                team.getWins(),
                team.getDraws(),
                team.getLosses(),
                team.getGoalDifference(),
                team.getGoalsScored(),
                team.getGoalsConceded(),
                team.getLast5Games(),
                team.getPoints()
        );
    }

    private Team convertToEntity(TeamDto teamDto) {
        return new Team(
                teamDto.getId(),
                teamDto.getName(),
                teamDto.getPlayed(),
                teamDto.getWins(),
                teamDto.getDraws(),
                teamDto.getLosses(),
                teamDto.getGoalDifference(),
                teamDto.getGoalsScored(),
                teamDto.getGoalsConceded(),
                teamDto.getLast5Games(),
                teamDto.getPoints()
        );
    }

    @Override
    public TeamDto createTeam(TeamDto teamDto) {
        Team team = convertToEntity(teamDto);
        if (team.getPlayed() == 0) {
            team.setWins(0);
            team.setDraws(0);
            team.setLosses(0);
            team.setGoalDifference(0);
            team.setGoalsScored(0);
            team.setGoalsConceded(0);
            team.setLast5Games("");
            team.setPoints(0);
        }
        Team savedTeam = teamRepositoryImpl.save(team);
        return convertToDTO(savedTeam);
    }

    @Override
    public TeamDto getTeamById(int id) {
        Team team = teamRepositoryImpl.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + id));
        return convertToDTO(team);
    }

    @Override
    public List<TeamDto> getAllTeams() {
        return teamRepositoryImpl.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeamDto updateTeam(int id, TeamDto teamDto) {
        Team existingTeam = teamRepositoryImpl.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + id));
        existingTeam.setName(teamDto.getName());
        existingTeam.setPlayed(teamDto.getPlayed());
        existingTeam.setWins(teamDto.getWins());
        existingTeam.setDraws(teamDto.getDraws());
        existingTeam.setLosses(teamDto.getLosses());
        existingTeam.setGoalDifference(teamDto.getGoalDifference());
        existingTeam.setGoalsScored(teamDto.getGoalsScored());
        existingTeam.setGoalsConceded(teamDto.getGoalsConceded());
        existingTeam.setLast5Games(teamDto.getLast5Games());
        existingTeam.setPoints(teamDto.getPoints());

        teamRepositoryImpl.update(existingTeam);
        return convertToDTO(existingTeam);
    }

    @Override
    public void deleteTeam(int id) {
        teamRepositoryImpl.deleteById(id);
    }

    @Override
    public List<TeamDto> getTeamsByTournamentId(Long tournamentId) {
        return teamRepositoryImpl.findByTournamentId(tournamentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void addTeamToTournament(int teamId, Long tournamentId) {
        teamRepositoryImpl.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + teamId));

        teamRepositoryImpl.addTeamToTournament(teamId, tournamentId);
    }

    @Override
    public void removeTeamFromTournament(int teamId, Long tournamentId) {
        teamRepositoryImpl.removeTeamFromTournament(teamId, tournamentId);
    }

    @Override
    public void updateTeamStats(int teamId, int goalsScored, int goalsConceded, String result) {
        Team team = teamRepositoryImpl.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + teamId));

        team.setPlayed(team.getPlayed() + 1);
        team.setGoalsScored(team.getGoalsScored() + goalsScored);
        team.setGoalsConceded(team.getGoalsConceded() + goalsConceded);
        team.setGoalDifference(team.getGoalsScored() - team.getGoalsConceded());
        String last5Games = team.getLast5Games();
        if (last5Games.length() >= 5) {
            last5Games = last5Games.substring(1);
        }

        switch (result) {
            case "W":
                team.setWins(team.getWins() + 1);
                team.setPoints(team.getPoints() + 3);
                last5Games += "W";
                break;
            case "D":
                team.setDraws(team.getDraws() + 1);
                team.setPoints(team.getPoints() + 1);
                last5Games += "D";
                break;
            case "L":
                team.setLosses(team.getLosses() + 1);
                last5Games += "L";
                break;
            default:
                throw new IllegalArgumentException("Invalid match result: " + result);
        }

        team.setLast5Games(last5Games);
        teamRepositoryImpl.update(team);
    }
}

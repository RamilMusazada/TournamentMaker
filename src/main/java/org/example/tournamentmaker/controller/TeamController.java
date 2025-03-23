package org.example.tournamentmaker.controller;


import org.example.tournamentmaker.dto.TeamDto;
import org.example.tournamentmaker.impl.TeamServiceImpl;
import org.example.tournamentmaker.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamServiceImpl teamServiceImpl;
    @Autowired
    public TeamController(TeamServiceImpl teamServiceImpl) {
        this.teamServiceImpl = teamServiceImpl;
    }

    @PostMapping
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto teamDto) {
        TeamDto createdTeam = teamServiceImpl.createTeam(teamDto);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeamById(@PathVariable int id) {
        TeamDto team = teamServiceImpl.getTeamById(id);
        return ResponseEntity.ok(team);
    }

    @GetMapping
    public ResponseEntity<List<TeamDto>> getAllTeams() {
        List<TeamDto> teams = teamServiceImpl.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable int id, @RequestBody TeamDto teamDTO) {
        TeamDto updatedTeam = teamServiceImpl.updateTeam(id, teamDTO);
        return ResponseEntity.ok(updatedTeam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable int id) {
        teamServiceImpl.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tournament/{tournamentId}")
    public ResponseEntity<List<TeamDto>> getTeamsByTournamentId(@PathVariable Long tournamentId) {
        List<TeamDto> teams = teamServiceImpl.getTeamsByTournamentId(tournamentId);
        return ResponseEntity.ok(teams);
    }

    @PostMapping("/{teamId}/tournament/{tournamentId}")
    public ResponseEntity<Void> addTeamToTournament(@PathVariable int teamId, @PathVariable Long tournamentId) {
        teamServiceImpl.addTeamToTournament(teamId, tournamentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{teamId}/tournament/{tournamentId}")
    public ResponseEntity<Void> removeTeamFromTournament(@PathVariable int teamId, @PathVariable Long tournamentId) {
        teamServiceImpl.removeTeamFromTournament(teamId, tournamentId);
        return ResponseEntity.noContent().build();
    }
}
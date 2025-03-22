package org.example.tournamentmaker.controller;

import org.example.tournamentmaker.dto.TournamentDto;
import org.example.tournamentmaker.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    @Autowired
    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @PostMapping
    public ResponseEntity<TournamentDto> createTournament(@RequestBody TournamentDto tournamentDto) {
        TournamentDto createdTournament = tournamentService.createTournament(tournamentDto);
        return new ResponseEntity<>(createdTournament, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentDto> getTournamentById(@PathVariable Long id) {
        TournamentDto tournament = tournamentService.getTournamentById(id);
        return ResponseEntity.ok(tournament);
    }

    @GetMapping
    public ResponseEntity<List<TournamentDto>> getAllTournaments() {
        List<TournamentDto> tournaments = tournamentService.getAllTournaments();
        return ResponseEntity.ok(tournaments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TournamentDto> updateTournament(@PathVariable Long id, @RequestBody TournamentDto tournamentDto) {
        TournamentDto updatedTournament = tournamentService.updateTournament(id, tournamentDto);
        return ResponseEntity.ok(updatedTournament);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        tournamentService.deleteTournament(id);
        return ResponseEntity.noContent().build();
    }
}
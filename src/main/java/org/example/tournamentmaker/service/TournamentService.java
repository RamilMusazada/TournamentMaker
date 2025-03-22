package org.example.tournamentmaker.service;

import org.example.tournamentmaker.dto.TournamentDto;
import org.example.tournamentmaker.entity.Tournament;
import org.example.tournamentmaker.exception.ResourceNotFoundException;
import org.example.tournamentmaker.impl.TournamentServiceImpl;
import org.example.tournamentmaker.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TournamentService implements TournamentServiceImpl {

    private final TournamentRepository tournamentRepository;
    @Autowired
    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }


    @Override
    public TournamentDto createTournament(TournamentDto tournamentDtO) {
        Tournament tournament = convertToEntity(tournamentDtO);
        Tournament savedTournament = tournamentRepository.save(tournament);
        return convertToDto(savedTournament);
    }

    @Override
    public TournamentDto getTournamentById(Long id) {
        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament not found with id: " + id));
        return convertToDto(tournament);
    }


    @Override
    public List<TournamentDto> getAllTournaments() {
        return tournamentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TournamentDto updateTournament(Long id, TournamentDto tournamentDto) {
        Tournament existingTournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament not found with id: " + id));

        existingTournament.setName(tournamentDto.getName());
        existingTournament.setStartDate(tournamentDto.getStartDate());
        existingTournament.setEndDate(tournamentDto.getEndDate());
        existingTournament.setType(tournamentDto.getType());
        existingTournament.setStatus(tournamentDto.getStatus());

        tournamentRepository.update(existingTournament);

        return convertToDto(existingTournament);
    }

    @Override
    public void deleteTournament(Long id) {
        tournamentRepository.deleteById(id);
    }

    private TournamentDto convertToDto(Tournament tournament) {
        return new TournamentDto(
                tournament.getId(),
                tournament.getName(),
                tournament.getStartDate(),
                tournament.getEndDate(),
                tournament.getType(),
                tournament.getStatus()
        );
    }

    private Tournament convertToEntity(TournamentDto tournamentDto) {
        Tournament tournament = new Tournament();
        tournament.setId(tournamentDto.getId());
        tournament.setName(tournamentDto.getName());
        tournament.setStartDate(tournamentDto.getStartDate());
        tournament.setEndDate(tournamentDto.getEndDate());
        tournament.setType(tournamentDto.getType());
        tournament.setStatus(tournamentDto.getStatus() != null ? tournamentDto.getStatus() : "upcoming");
        return tournament;
    }
}
package org.example.tournamentmaker.impl;



import org.example.tournamentmaker.entity.Tournament;

import java.util.List;
import java.util.Optional;

public interface TournamentRepositoryImpl {
    Tournament save(Tournament tournament);

    Optional<Tournament> findById(Long id);

    List<Tournament> findAll();

    void update(Tournament tournament);

    void deleteById(Long id);
}
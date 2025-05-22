package com.valeriobarbershop.backend.repository;

import com.valeriobarbershop.backend.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByClienteEmail(String email);
    List<Prenotazione> findByDataOraBetween(LocalDateTime inizio, LocalDateTime fine);
}
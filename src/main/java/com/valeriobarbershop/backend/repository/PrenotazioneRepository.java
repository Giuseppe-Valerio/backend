package com.valeriobarbershop.backend.repository;

import com.valeriobarbershop.backend.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
}
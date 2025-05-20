package com.valeriobarbershop.backend.controller;

import com.valeriobarbershop.backend.model.Prenotazione;
import com.valeriobarbershop.backend.repository.PrenotazioneRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")
public class PrenotazioneController {

    private final PrenotazioneRepository repository;

    public PrenotazioneController(PrenotazioneRepository repository) {
        this.repository = repository;
    }

    // GET /api/prenotazioni - Tutte le prenotazioni
    @GetMapping
    public List<Prenotazione> getAll() {
        return repository.findAll();
    }

    // POST /api/prenotazioni - Crea nuova prenotazione
    @PostMapping
    public Prenotazione create(@RequestBody Prenotazione prenotazione) {
        return repository.save(prenotazione);
    }

    // GET /api/prenotazioni/me - Solo prenotazioni dell'utente loggato (da migliorare in futuro)
    @GetMapping("/me")
    public String getPrenotazioniUtenteLoggato() {
        return "Questa è la lista delle prenotazioni dell'utente loggato";
    }
}
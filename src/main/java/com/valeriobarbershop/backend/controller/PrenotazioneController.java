package com.valeriobarbershop.backend.controller;

import com.valeriobarbershop.backend.model.Prenotazione;
import com.valeriobarbershop.backend.model.StatoPrenotazione;
import com.valeriobarbershop.backend.model.Utente;
import com.valeriobarbershop.backend.repository.PrenotazioneRepository;
import com.valeriobarbershop.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")
public class PrenotazioneController {

    private final PrenotazioneRepository repository;

    @Autowired
    private AuthService authService;

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

    // GET /api/prenotazioni/me - Solo prenotazioni dell'utente loggato
    @GetMapping("/me")
    public List<Prenotazione> getPrenotazioniUtenteLoggato() {
        Utente utente = authService.getUtenteAutenticato();
        return repository.findByClienteEmail(utente.getEmail());
    }
    @PutMapping("/{id}")
    public Prenotazione updatePrenotazione(@PathVariable Long id, @RequestBody Prenotazione prenotazioneAggiornata) {
        return repository.findById(id)
                .map(prenotazione -> {
                    prenotazione.setDataOra(prenotazioneAggiornata.getDataOra());
                    prenotazione.setServizio(prenotazioneAggiornata.getServizio());
                    return repository.save(prenotazione);
                })
                .orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));
    }

    @PatchMapping("/{id}/annulla")
    public Prenotazione annullaPrenotazione(@PathVariable Long id) {
        return repository.findById(id)
                .map(prenotazione -> {
                    prenotazione.setStato(StatoPrenotazione.ANNULLATA);
                    return repository.save(prenotazione);
                })
                .orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));
    }
}
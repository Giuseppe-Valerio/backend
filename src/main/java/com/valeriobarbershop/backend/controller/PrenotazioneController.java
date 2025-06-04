package com.valeriobarbershop.backend.controller;

import com.valeriobarbershop.backend.dto.prenotazione.SlotOrarioDTO;
import com.valeriobarbershop.backend.model.Prenotazione;
import com.valeriobarbershop.backend.model.StatoPrenotazione;
import com.valeriobarbershop.backend.model.Utente;
import com.valeriobarbershop.backend.repository.PrenotazioneRepository;
import com.valeriobarbershop.backend.repository.ServizioRepository;
import com.valeriobarbershop.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")
public class PrenotazioneController {

    private final PrenotazioneRepository repository;
    private final ServizioRepository servizioRepository; // 👈 Iniezione aggiunta

    @Autowired
    private AuthService authService;

    public PrenotazioneController(
            PrenotazioneRepository repository,
            ServizioRepository servizioRepository) { // 👈 Costruttore con iniezione
        this.repository = repository;
        this.servizioRepository = servizioRepository;
    }

    // GET /api/prenotazioni - Tutte le prenotazioni
    @GetMapping
    public List<Prenotazione> getAll() {
        return repository.findAll();
    }

    // POST /api/prenotazioni - Crea nuova prenotazione
    @PostMapping
    public ResponseEntity<?> creaPrenotazione(@RequestBody SlotOrarioDTO dto) {
        // Converti il DTO in Prenotazione
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setDataOra(dto.getDataOra());

        // Recupera il servizio dal repository usando l'id del DTO
        prenotazione.setServizio(servizioRepository.findById(dto.getServizioId())
                .orElseThrow(() -> new RuntimeException("Servizio non trovato")));

        // Imposta l'utente autenticato come cliente
        Utente utente = authService.getUtenteAutenticato();
        prenotazione.setCliente(utente);

        // Salva la prenotazione nel database
        return ResponseEntity.ok(repository.save(prenotazione));
    }

    // GET /api/prenotazioni/me - Solo prenotazioni dell'utente loggato
    @GetMapping("/me")
    public List<Prenotazione> getPrenotazioniUtenteLoggato() {
        Utente utente = authService.getUtenteAutenticato();
        return repository.findByClienteEmail(utente.getEmail());
    }

    // PUT /api/prenotazioni/{id} - Aggiorna una prenotazione
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

    // PATCH /api/prenotazioni/{id}/annulla - Annulla una prenotazione
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
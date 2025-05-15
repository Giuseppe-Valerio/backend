package com.valeriobarbershop.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prenotazioni")
public class PrenotazioneController {

    @GetMapping("/me")
    public String getPrenotazioniUtenteLoggato() {
        return "Questa è la lista delle prenotazioni dell'utente loggato";
    }
}
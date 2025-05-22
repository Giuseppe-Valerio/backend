package com.valeriobarbershop.backend.controller;

import com.valeriobarbershop.backend.dto.prenotazione.SlotOrarioDTO;
import com.valeriobarbershop.backend.model.Prenotazione;
import com.valeriobarbershop.backend.repository.PrenotazioneRepository;
import com.valeriobarbershop.backend.model.Servizio;
import com.valeriobarbershop.backend.repository.ServizioRepository;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni/slots")
public class PrenotazioneAdvancedController {

    private final PrenotazioneRepository prenotazioneRepository;
    private final ServizioRepository servizioRepository;

    public PrenotazioneAdvancedController(PrenotazioneRepository prenotazioneRepository,
                                          ServizioRepository servizioRepository) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.servizioRepository = servizioRepository;
    }

    @GetMapping
    public List<SlotOrarioDTO> getSlotDisponibili(@RequestParam Long servizioId,
                                                  @RequestParam String data) {

        // Parsing della data
        LocalDate dataSelezionata = LocalDate.parse(data);

        // Recupero del servizio
        Servizio servizio = servizioRepository.findById(servizioId)
                .orElseThrow(() -> new RuntimeException("Servizio non trovato"));

        int durataMinuti = servizio.getDurataMinuti();

        // Recupero prenotazioni nella giornata selezionata
        LocalDateTime inizioGiornata = dataSelezionata.atStartOfDay();
        LocalDateTime fineGiornata = dataSelezionata.atTime(23, 59);

        List<Prenotazione> prenotazioni = prenotazioneRepository.findByDataOraBetween(inizioGiornata, fineGiornata);

        // Genera tutti gli slot della giornata
        LocalTime oraInizio = LocalTime.of(9, 0);
        LocalTime oraFine = LocalTime.of(19, 0);

        List<SlotOrarioDTO> tuttiGliSlot = new ArrayList<>();

        while (!oraInizio.isAfter(oraFine)) {
            LocalTime fineSlot = oraInizio.plusMinutes(durataMinuti);

            if (fineSlot.isAfter(oraFine)) break;

            boolean disponibile = true;

            for (Prenotazione p : prenotazioni) {
                LocalTime prenOra = p.getDataOra().toLocalTime();
                if ((oraInizio.isBefore(prenOra) && fineSlot.isAfter(prenOra)) ||
                        oraInizio.equals(prenOra)) {
                    disponibile = false;
                    break;
                }
            }

            tuttiGliSlot.add(new SlotOrarioDTO(oraInizio, fineSlot, disponibile));
            oraInizio = fineSlot;
        }

        return tuttiGliSlot;
    }
}
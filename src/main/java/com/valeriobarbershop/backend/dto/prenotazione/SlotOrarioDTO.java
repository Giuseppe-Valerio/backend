package com.valeriobarbershop.backend.dto.prenotazione;

import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotOrarioDTO {

    private LocalDateTime dataOra;  // Usato per la prenotazione
    private Long servizioId;        // Usato per recuperare il servizio

    // Campi opzionali (usati probabilmente per mostrare gli slot orari)
    private LocalTime inizio;
    private LocalTime fine;
    private boolean disponibile;

    // Costruttore per usare in PrenotazioneAdvancedController
    public SlotOrarioDTO(LocalTime inizio, LocalTime fine, boolean disponibile) {
        this.inizio = inizio;
        this.fine = fine;
        this.disponibile = disponibile;
    }
}
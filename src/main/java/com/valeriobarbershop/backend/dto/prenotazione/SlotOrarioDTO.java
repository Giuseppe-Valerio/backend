package com.valeriobarbershop.backend.dto.prenotazione;

import java.time.LocalTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotOrarioDTO {
    private LocalTime inizio;
    private LocalTime fine;
    private boolean disponibile;
}
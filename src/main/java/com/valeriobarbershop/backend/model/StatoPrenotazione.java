package com.valeriobarbershop.backend.model;

import lombok.Getter;

@Getter
public enum StatoPrenotazione {
    CONFERMATA("Confermata"),
    ANNULLATA("Annullata");

    private final String label;

    StatoPrenotazione(String label) {
        this.label = label;
    }
}
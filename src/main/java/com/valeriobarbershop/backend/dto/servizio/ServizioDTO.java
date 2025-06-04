package com.valeriobarbershop.backend.dto.servizio;

import lombok.Data;

@Data
public class ServizioDTO {
    private Long id;
    private String nome;
    private String descrizione;
    private Integer durataMinuti;
    private Double prezzo;

    public ServizioDTO(Long id, String nome, String descrizione, Integer durataMinuti, Double prezzo) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.durataMinuti = durataMinuti;
        this.prezzo = prezzo;
    }
}
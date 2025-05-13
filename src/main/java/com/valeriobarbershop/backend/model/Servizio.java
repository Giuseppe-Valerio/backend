package com.valeriobarbershop.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "servizi")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servizio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servizio_seq")
    @SequenceGenerator(name = "servizio_seq", sequenceName = "servizio_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String descrizione;

    @Column(nullable = false)
    private Integer durataMinuti;

    @Column(nullable = false)
    private Double prezzo;
}
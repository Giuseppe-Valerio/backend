package com.valeriobarbershop.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "prenotazioni")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prenotazione_seq")
    @SequenceGenerator(name = "prenotazione_seq", sequenceName = "prenotazione_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Utente cliente;

    @ManyToOne
    private Servizio servizio;

    @Column(nullable = false)
    private LocalDateTime dataOra;

    private String stato; // es. "confermata", "annullata"
}
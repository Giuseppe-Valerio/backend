package com.valeriobarbershop.backend.utils;

import com.valeriobarbershop.backend.model.Servizio;
import com.valeriobarbershop.backend.repository.PrenotazioneRepository;
import com.valeriobarbershop.backend.repository.ServizioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ServizioRepository servizioRepository;
    private final PrenotazioneRepository prenotazioneRepository;

    public DataLoader(ServizioRepository servizioRepository,
                      PrenotazioneRepository prenotazioneRepository) {
        this.servizioRepository = servizioRepository;
        this.prenotazioneRepository = prenotazioneRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Cancella le prenotazioni prima dei servizi
        prenotazioneRepository.deleteAll();
        servizioRepository.deleteAll();

        // Inserisci solo se NON ci sono servizi
        if (servizioRepository.count() == 0) {
            servizioRepository.save(new Servizio(null, "Taglio uomo", "Taglio completo uomo", 30, 15.0));
            servizioRepository.save(new Servizio(null, "Taglio barba", "Definizione della barba", 20, 12.0));
            servizioRepository.save(new Servizio(null, "Shampoo", "Lavaggio capelli professionale", 15, 10.0));
            servizioRepository.save(new Servizio(null, "Colore", "Colore capelli professionale", 60, 30.0));
        }
    }
}
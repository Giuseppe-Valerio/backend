package com.valeriobarbershop.backend.controller;

import com.valeriobarbershop.backend.dto.servizio.ServizioDTO;
import com.valeriobarbershop.backend.model.Servizio;
import com.valeriobarbershop.backend.repository.ServizioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/servizi")
public class ServizioController {

    private final ServizioRepository repository;

    public ServizioController(ServizioRepository repository) {
        this.repository = repository;
    }

    // GET - Tutti i servizi (con DTO)
    @GetMapping
    public List<ServizioDTO> getAll() {
        return repository.findAll().stream()
                .map(servizio -> new ServizioDTO(
                        servizio.getId(),
                        servizio.getNome(),
                        servizio.getDescrizione(),
                        servizio.getDurataMinuti(),
                        servizio.getPrezzo()))
                .collect(Collectors.toList());
    }

    // POST - Crea un nuovo servizio
    @PostMapping
    public Servizio create(@RequestBody Servizio servizio) {
        return repository.save(servizio);
    }

    // PUT - Aggiorna un servizio esistente
    @PutMapping("/{id}")
    public Servizio update(@PathVariable Long id, @RequestBody Servizio servizioAggiornato) {
        return repository.findById(id)
                .map(servizio -> {
                    servizio.setNome(servizioAggiornato.getNome());
                    servizio.setDescrizione(servizioAggiornato.getDescrizione());
                    servizio.setDurataMinuti(servizioAggiornato.getDurataMinuti());
                    servizio.setPrezzo(servizioAggiornato.getPrezzo());
                    return repository.save(servizio);
                })
                .orElseThrow(() -> new RuntimeException("Servizio non trovato"));
    }

    // DELETE - Cancella un servizio
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
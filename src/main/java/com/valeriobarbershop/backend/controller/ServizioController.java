package com.valeriobarbershop.backend.controller;

import com.valeriobarbershop.backend.model.Servizio;
import com.valeriobarbershop.backend.repository.ServizioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servizi")
public class ServizioController {

    private final ServizioRepository repository;

    public ServizioController(ServizioRepository repository) {
        this.repository = repository;
    }


    @GetMapping
    public List<Servizio> getAll() {
        return repository.findAll();
    }


    @PostMapping
    public Servizio create(@RequestBody Servizio servizio) {
        return repository.save(servizio);
    }
}
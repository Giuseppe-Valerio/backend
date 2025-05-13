package com.valeriobarbershop.backend.repository;

import com.valeriobarbershop.backend.model.Servizio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServizioRepository extends JpaRepository<Servizio, Long> {
}
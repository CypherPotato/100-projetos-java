package com.exemplo.reservasalas.repository;

import com.exemplo.reservasalas.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {
}

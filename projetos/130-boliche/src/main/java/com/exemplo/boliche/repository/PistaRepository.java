package com.exemplo.boliche.repository;

import com.exemplo.boliche.model.Pista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PistaRepository extends JpaRepository<Pista, Long> {
}

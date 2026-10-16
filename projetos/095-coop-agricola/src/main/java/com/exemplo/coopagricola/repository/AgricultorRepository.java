package com.exemplo.coopagricola.repository;

import com.exemplo.coopagricola.model.Agricultor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgricultorRepository extends JpaRepository<Agricultor, Long> {
}

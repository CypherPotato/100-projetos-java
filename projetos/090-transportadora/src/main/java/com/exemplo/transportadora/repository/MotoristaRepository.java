package com.exemplo.transportadora.repository;

import com.exemplo.transportadora.model.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotoristaRepository extends JpaRepository<Motorista, Long> {
}

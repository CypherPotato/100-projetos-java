package com.exemplo.casacambio.repository;

import com.exemplo.casacambio.model.Moeda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoedaRepository extends JpaRepository<Moeda, Long> {
}

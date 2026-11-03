package com.exemplo.bancosangue.repository;

import com.exemplo.bancosangue.model.Doador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoadorRepository extends JpaRepository<Doador, Long> {
}

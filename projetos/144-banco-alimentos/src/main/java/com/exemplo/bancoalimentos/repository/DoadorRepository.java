package com.exemplo.bancoalimentos.repository;

import com.exemplo.bancoalimentos.model.Doador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoadorRepository extends JpaRepository<Doador, Long> {
}

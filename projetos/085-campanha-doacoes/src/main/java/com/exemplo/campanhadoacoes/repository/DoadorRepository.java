package com.exemplo.campanhadoacoes.repository;

import com.exemplo.campanhadoacoes.model.Doador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoadorRepository extends JpaRepository<Doador, Long> {
}

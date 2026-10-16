package com.exemplo.caixadiario.repository;

import com.exemplo.caixadiario.model.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaixaRepository extends JpaRepository<Caixa, Long> {
}

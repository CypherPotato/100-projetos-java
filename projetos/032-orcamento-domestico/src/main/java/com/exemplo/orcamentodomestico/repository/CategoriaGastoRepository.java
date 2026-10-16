package com.exemplo.orcamentodomestico.repository;

import com.exemplo.orcamentodomestico.model.CategoriaGasto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaGastoRepository extends JpaRepository<CategoriaGasto, Long> {
}

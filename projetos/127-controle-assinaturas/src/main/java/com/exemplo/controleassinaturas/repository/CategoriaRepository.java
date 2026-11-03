package com.exemplo.controleassinaturas.repository;

import com.exemplo.controleassinaturas.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}

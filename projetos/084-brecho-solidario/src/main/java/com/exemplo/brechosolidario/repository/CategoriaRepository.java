package com.exemplo.brechosolidario.repository;

import com.exemplo.brechosolidario.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}

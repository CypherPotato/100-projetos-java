package com.exemplo.lojaroupas.repository;

import com.exemplo.lojaroupas.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}

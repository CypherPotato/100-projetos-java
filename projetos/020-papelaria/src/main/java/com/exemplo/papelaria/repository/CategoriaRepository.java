package com.exemplo.papelaria.repository;

import com.exemplo.papelaria.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}

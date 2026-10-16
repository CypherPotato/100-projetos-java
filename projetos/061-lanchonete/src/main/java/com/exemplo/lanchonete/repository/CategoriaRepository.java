package com.exemplo.lanchonete.repository;

import com.exemplo.lanchonete.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}

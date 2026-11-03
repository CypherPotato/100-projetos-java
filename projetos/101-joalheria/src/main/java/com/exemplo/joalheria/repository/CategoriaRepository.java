package com.exemplo.joalheria.repository;

import com.exemplo.joalheria.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}

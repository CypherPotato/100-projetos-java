package com.exemplo.catalogoapis.repository;

import com.exemplo.catalogoapis.model.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipeRepository extends JpaRepository<Equipe, Long> {
}

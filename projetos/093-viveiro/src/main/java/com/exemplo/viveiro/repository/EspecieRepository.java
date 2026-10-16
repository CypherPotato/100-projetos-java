package com.exemplo.viveiro.repository;

import com.exemplo.viveiro.model.Especie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecieRepository extends JpaRepository<Especie, Long> {
}

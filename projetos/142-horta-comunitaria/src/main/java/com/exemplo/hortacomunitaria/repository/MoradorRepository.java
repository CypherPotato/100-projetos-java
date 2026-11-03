package com.exemplo.hortacomunitaria.repository;

import com.exemplo.hortacomunitaria.model.Morador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoradorRepository extends JpaRepository<Morador, Long> {
}

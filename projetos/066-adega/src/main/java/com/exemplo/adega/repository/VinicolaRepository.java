package com.exemplo.adega.repository;

import com.exemplo.adega.model.Vinicola;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VinicolaRepository extends JpaRepository<Vinicola, Long> {
}

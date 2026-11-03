package com.exemplo.perfumaria.repository;

import com.exemplo.perfumaria.model.Fabricante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FabricanteRepository extends JpaRepository<Fabricante, Long> {
}

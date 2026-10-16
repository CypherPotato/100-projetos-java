package com.exemplo.campanhadoacoes.repository;

import com.exemplo.campanhadoacoes.model.Campanha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampanhaRepository extends JpaRepository<Campanha, Long> {
}

package com.exemplo.clubexadrez.repository;

import com.exemplo.clubexadrez.model.Torneio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TorneioRepository extends JpaRepository<Torneio, Long> {
}

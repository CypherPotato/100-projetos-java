package com.exemplo.agenciaviagens.repository;

import com.exemplo.agenciaviagens.model.Pacote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacoteRepository extends JpaRepository<Pacote, Long> {
}

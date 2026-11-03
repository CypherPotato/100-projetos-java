package com.exemplo.clubexadrez.repository;

import com.exemplo.clubexadrez.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogadorRepository extends JpaRepository<Jogador, Long> {
}

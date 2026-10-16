package com.exemplo.campeonato.repository;

import com.exemplo.campeonato.model.Jogador;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogadorRepository extends JpaRepository<Jogador, Long> {

    List<Jogador> findByEquipeId(Long equipeId);
}

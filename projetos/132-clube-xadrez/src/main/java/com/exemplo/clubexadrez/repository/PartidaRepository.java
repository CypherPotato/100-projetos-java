package com.exemplo.clubexadrez.repository;

import com.exemplo.clubexadrez.model.Partida;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidaRepository extends JpaRepository<Partida, Long> {

    List<Partida> findByBrancasId(Long brancasId);

    List<Partida> findByTorneioId(Long torneioId);
}

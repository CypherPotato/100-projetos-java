package com.exemplo.lojagames.repository;

import com.exemplo.lojagames.model.Jogo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogoRepository extends JpaRepository<Jogo, Long> {

    List<Jogo> findByPlataformaId(Long plataformaId);
}

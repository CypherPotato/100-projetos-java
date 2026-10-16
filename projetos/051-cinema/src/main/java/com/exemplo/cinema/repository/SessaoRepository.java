package com.exemplo.cinema.repository;

import com.exemplo.cinema.model.Sessao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    List<Sessao> findByFilmeId(Long filmeId);
}

package com.exemplo.reforcoescolar.repository;

import com.exemplo.reforcoescolar.model.Sessao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    List<Sessao> findByAlunoId(Long alunoId);
}

package com.exemplo.cooperativa.repository;

import com.exemplo.cooperativa.model.Contribuicao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContribuicaoRepository extends JpaRepository<Contribuicao, Long> {

    List<Contribuicao> findByCooperadoId(Long cooperadoId);
}

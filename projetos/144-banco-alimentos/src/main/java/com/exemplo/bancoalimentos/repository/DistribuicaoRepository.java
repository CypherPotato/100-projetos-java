package com.exemplo.bancoalimentos.repository;

import com.exemplo.bancoalimentos.model.Distribuicao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistribuicaoRepository extends JpaRepository<Distribuicao, Long> {

    List<Distribuicao> findByAlimentoId(Long alimentoId);
}

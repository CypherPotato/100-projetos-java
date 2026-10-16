package com.exemplo.frotaveiculos.repository;

import com.exemplo.frotaveiculos.model.Manutencao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {

    List<Manutencao> findByVeiculoId(Long veiculoId);
}

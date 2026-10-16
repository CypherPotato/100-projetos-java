package com.exemplo.frotaveiculos.repository;

import com.exemplo.frotaveiculos.model.Abastecimento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbastecimentoRepository extends JpaRepository<Abastecimento, Long> {

    List<Abastecimento> findByVeiculoId(Long veiculoId);
}

package com.exemplo.locadora.repository;

import com.exemplo.locadora.model.Locacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocacaoRepository extends JpaRepository<Locacao, Long> {

    List<Locacao> findByVeiculoId(Long veiculoId);
}

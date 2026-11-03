package com.exemplo.casacambio.repository;

import com.exemplo.casacambio.model.Operacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperacaoRepository extends JpaRepository<Operacao, Long> {

    List<Operacao> findByMoedaId(Long moedaId);
}

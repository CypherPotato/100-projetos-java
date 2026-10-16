package com.exemplo.fornecedores.repository;

import com.exemplo.fornecedores.model.Cotacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CotacaoRepository extends JpaRepository<Cotacao, Long> {

    List<Cotacao> findByFornecedorId(Long fornecedorId);
}

package com.exemplo.controleestoque.repository;

import com.exemplo.controleestoque.model.Movimentacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    List<Movimentacao> findByProdutoId(Long produtoId);
}

package com.exemplo.orcamentodomestico.repository;

import com.exemplo.orcamentodomestico.model.Lancamento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    List<Lancamento> findByCategoriaId(Long categoriaId);
}

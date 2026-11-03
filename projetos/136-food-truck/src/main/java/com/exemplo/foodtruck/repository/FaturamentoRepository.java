package com.exemplo.foodtruck.repository;

import com.exemplo.foodtruck.model.Faturamento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaturamentoRepository extends JpaRepository<Faturamento, Long> {

    List<Faturamento> findByPontoVendaId(Long pontoVendaId);
}

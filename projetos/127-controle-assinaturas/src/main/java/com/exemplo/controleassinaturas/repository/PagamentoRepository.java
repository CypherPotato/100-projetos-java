package com.exemplo.controleassinaturas.repository;

import com.exemplo.controleassinaturas.model.Pagamento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    List<Pagamento> findByAssinaturaId(Long assinaturaId);
}

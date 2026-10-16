package com.exemplo.oficinamecanica.repository;

import com.exemplo.oficinamecanica.model.OrdemServico;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {

    List<OrdemServico> findByVeiculoId(Long veiculoId);
}

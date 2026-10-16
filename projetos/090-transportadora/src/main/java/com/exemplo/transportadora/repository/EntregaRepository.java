package com.exemplo.transportadora.repository;

import com.exemplo.transportadora.model.Entrega;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {

    List<Entrega> findByMotoristaId(Long motoristaId);

    List<Entrega> findByVeiculoId(Long veiculoId);
}

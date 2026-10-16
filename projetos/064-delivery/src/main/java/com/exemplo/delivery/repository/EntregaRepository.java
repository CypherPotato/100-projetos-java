package com.exemplo.delivery.repository;

import com.exemplo.delivery.model.Entrega;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {

    List<Entrega> findByRestauranteId(Long restauranteId);

    List<Entrega> findByEntregadorId(Long entregadorId);
}

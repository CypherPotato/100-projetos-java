package com.exemplo.restaurante.repository;

import com.exemplo.restaurante.model.Pedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByMesaId(Long mesaId);
}

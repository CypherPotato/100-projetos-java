package com.exemplo.lavanderia.repository;

import com.exemplo.lavanderia.model.PedidoLavagem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoLavagemRepository extends JpaRepository<PedidoLavagem, Long> {

    List<PedidoLavagem> findByClienteId(Long clienteId);
}

package com.exemplo.chaveiro.repository;

import com.exemplo.chaveiro.model.ChamadoChaveiro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoChaveiroRepository extends JpaRepository<ChamadoChaveiro, Long> {

    List<ChamadoChaveiro> findByClienteId(Long clienteId);
}

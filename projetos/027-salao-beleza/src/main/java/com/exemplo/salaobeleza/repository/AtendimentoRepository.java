package com.exemplo.salaobeleza.repository;

import com.exemplo.salaobeleza.model.Atendimento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    List<Atendimento> findByClienteId(Long clienteId);

    List<Atendimento> findByProcedimentoId(Long procedimentoId);
}

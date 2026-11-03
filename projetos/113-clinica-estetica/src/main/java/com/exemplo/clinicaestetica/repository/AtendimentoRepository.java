package com.exemplo.clinicaestetica.repository;

import com.exemplo.clinicaestetica.model.Atendimento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    List<Atendimento> findByProcedimentoId(Long procedimentoId);
}

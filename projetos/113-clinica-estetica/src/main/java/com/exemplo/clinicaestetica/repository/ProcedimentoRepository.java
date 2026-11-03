package com.exemplo.clinicaestetica.repository;

import com.exemplo.clinicaestetica.model.Procedimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {
}

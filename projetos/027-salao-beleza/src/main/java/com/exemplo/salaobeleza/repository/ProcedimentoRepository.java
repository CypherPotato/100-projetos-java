package com.exemplo.salaobeleza.repository;

import com.exemplo.salaobeleza.model.Procedimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {
}

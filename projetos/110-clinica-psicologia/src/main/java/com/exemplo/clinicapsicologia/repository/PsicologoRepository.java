package com.exemplo.clinicapsicologia.repository;

import com.exemplo.clinicapsicologia.model.Psicologo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PsicologoRepository extends JpaRepository<Psicologo, Long> {
}

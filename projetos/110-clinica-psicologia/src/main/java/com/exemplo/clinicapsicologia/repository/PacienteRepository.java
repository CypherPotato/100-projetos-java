package com.exemplo.clinicapsicologia.repository;

import com.exemplo.clinicapsicologia.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}

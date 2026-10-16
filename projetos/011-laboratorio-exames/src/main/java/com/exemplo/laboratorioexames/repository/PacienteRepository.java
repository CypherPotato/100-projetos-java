package com.exemplo.laboratorioexames.repository;

import com.exemplo.laboratorioexames.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}

package com.exemplo.nutricionista.repository;

import com.exemplo.nutricionista.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}

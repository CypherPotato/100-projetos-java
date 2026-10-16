package com.exemplo.clinicamedica.repository;

import com.exemplo.clinicamedica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}

package com.exemplo.clinicamedica.repository;

import com.exemplo.clinicamedica.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
}

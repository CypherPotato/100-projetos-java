package com.exemplo.clinicamedica.repository;

import com.exemplo.clinicamedica.model.Consulta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findByMedicoId(Long medicoId);

    List<Consulta> findByPacienteId(Long pacienteId);
}

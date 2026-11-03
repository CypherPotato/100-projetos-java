package com.exemplo.clinicapsicologia.repository;

import com.exemplo.clinicapsicologia.model.Sessao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    List<Sessao> findByPsicologoId(Long psicologoId);

    List<Sessao> findByPacienteId(Long pacienteId);
}

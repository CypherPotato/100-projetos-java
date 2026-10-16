package com.exemplo.laboratorioexames.repository;

import com.exemplo.laboratorioexames.model.Exame;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExameRepository extends JpaRepository<Exame, Long> {

    List<Exame> findByTipoExameId(Long tipoExameId);

    List<Exame> findByPacienteId(Long pacienteId);
}

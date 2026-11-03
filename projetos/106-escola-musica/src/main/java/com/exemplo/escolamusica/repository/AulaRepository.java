package com.exemplo.escolamusica.repository;

import com.exemplo.escolamusica.model.Aula;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AulaRepository extends JpaRepository<Aula, Long> {

    List<Aula> findByInstrumentoId(Long instrumentoId);

    List<Aula> findByProfessorId(Long professorId);
}

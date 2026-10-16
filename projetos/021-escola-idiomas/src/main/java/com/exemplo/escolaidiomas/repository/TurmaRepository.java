package com.exemplo.escolaidiomas.repository;

import com.exemplo.escolaidiomas.model.Turma;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

    List<Turma> findByIdiomaId(Long idiomaId);

    List<Turma> findByProfessorId(Long professorId);
}

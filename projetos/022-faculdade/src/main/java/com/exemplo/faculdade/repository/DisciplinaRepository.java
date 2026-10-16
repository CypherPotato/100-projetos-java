package com.exemplo.faculdade.repository;

import com.exemplo.faculdade.model.Disciplina;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

    List<Disciplina> findByCursoId(Long cursoId);
}

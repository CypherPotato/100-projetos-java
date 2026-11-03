package com.exemplo.simuladosconcursos.repository;

import com.exemplo.simuladosconcursos.model.Concurso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcursoRepository extends JpaRepository<Concurso, Long> {
}

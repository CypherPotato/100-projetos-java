package com.exemplo.faculdade.repository;

import com.exemplo.faculdade.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}

package com.exemplo.escolamusica.repository;

import com.exemplo.escolamusica.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}

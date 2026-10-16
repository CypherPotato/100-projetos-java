package com.exemplo.escolaidiomas.repository;

import com.exemplo.escolaidiomas.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}

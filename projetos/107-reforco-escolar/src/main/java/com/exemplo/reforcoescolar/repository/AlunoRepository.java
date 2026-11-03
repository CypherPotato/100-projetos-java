package com.exemplo.reforcoescolar.repository;

import com.exemplo.reforcoescolar.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}

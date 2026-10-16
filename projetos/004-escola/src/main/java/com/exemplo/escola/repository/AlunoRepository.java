package com.exemplo.escola.repository;

import com.exemplo.escola.model.Aluno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    List<Aluno> findByTurmaId(Long turmaId);
}

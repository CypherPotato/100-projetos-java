package com.exemplo.academia.repository;

import com.exemplo.academia.model.Aluno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    List<Aluno> findByPlanoId(Long planoId);
}

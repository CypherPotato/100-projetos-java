package com.exemplo.bugs.repository;

import com.exemplo.bugs.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}

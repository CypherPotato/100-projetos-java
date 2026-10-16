package com.exemplo.gestaoprojetos.repository;

import com.exemplo.gestaoprojetos.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}

package com.exemplo.repositoriosnippets.repository;

import com.exemplo.repositoriosnippets.model.Linguagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinguagemRepository extends JpaRepository<Linguagem, Long> {
}

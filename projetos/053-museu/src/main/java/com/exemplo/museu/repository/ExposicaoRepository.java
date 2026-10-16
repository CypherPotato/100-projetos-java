package com.exemplo.museu.repository;

import com.exemplo.museu.model.Exposicao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExposicaoRepository extends JpaRepository<Exposicao, Long> {
}

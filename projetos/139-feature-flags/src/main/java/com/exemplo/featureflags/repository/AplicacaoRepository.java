package com.exemplo.featureflags.repository;

import com.exemplo.featureflags.model.Aplicacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AplicacaoRepository extends JpaRepository<Aplicacao, Long> {
}

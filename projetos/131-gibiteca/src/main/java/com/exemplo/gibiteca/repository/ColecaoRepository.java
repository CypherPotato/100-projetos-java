package com.exemplo.gibiteca.repository;

import com.exemplo.gibiteca.model.Colecao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColecaoRepository extends JpaRepository<Colecao, Long> {
}

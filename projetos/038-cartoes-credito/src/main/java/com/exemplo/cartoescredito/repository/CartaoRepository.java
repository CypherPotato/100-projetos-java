package com.exemplo.cartoescredito.repository;

import com.exemplo.cartoescredito.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}

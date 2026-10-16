package com.exemplo.cartoescredito.repository;

import com.exemplo.cartoescredito.model.Fatura;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaturaRepository extends JpaRepository<Fatura, Long> {

    List<Fatura> findByCartaoId(Long cartaoId);
}

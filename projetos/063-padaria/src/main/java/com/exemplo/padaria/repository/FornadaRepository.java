package com.exemplo.padaria.repository;

import com.exemplo.padaria.model.Fornada;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornadaRepository extends JpaRepository<Fornada, Long> {

    List<Fornada> findByPadeiroId(Long padeiroId);
}

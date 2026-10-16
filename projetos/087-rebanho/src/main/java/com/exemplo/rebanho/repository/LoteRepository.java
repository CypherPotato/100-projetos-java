package com.exemplo.rebanho.repository;

import com.exemplo.rebanho.model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoteRepository extends JpaRepository<Lote, Long> {
}

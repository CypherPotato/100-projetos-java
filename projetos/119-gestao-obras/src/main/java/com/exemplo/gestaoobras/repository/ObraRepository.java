package com.exemplo.gestaoobras.repository;

import com.exemplo.gestaoobras.model.Obra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObraRepository extends JpaRepository<Obra, Long> {
}

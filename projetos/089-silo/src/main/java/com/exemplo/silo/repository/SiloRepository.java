package com.exemplo.silo.repository;

import com.exemplo.silo.model.Silo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiloRepository extends JpaRepository<Silo, Long> {
}

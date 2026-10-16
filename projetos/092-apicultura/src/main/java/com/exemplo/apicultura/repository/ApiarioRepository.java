package com.exemplo.apicultura.repository;

import com.exemplo.apicultura.model.Apiario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiarioRepository extends JpaRepository<Apiario, Long> {
}

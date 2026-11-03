package com.exemplo.ouvidoria.repository;

import com.exemplo.ouvidoria.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
}

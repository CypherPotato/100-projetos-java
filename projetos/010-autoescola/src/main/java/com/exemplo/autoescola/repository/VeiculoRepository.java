package com.exemplo.autoescola.repository;

import com.exemplo.autoescola.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}

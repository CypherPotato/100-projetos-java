package com.exemplo.transporteescolar.repository;

import com.exemplo.transporteescolar.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}

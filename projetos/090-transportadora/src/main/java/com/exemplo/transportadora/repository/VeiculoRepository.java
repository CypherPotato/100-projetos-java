package com.exemplo.transportadora.repository;

import com.exemplo.transportadora.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}

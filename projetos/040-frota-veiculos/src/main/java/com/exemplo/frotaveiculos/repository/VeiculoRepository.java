package com.exemplo.frotaveiculos.repository;

import com.exemplo.frotaveiculos.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}

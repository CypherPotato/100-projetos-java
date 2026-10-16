package com.exemplo.lavarapido.repository;

import com.exemplo.lavarapido.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}

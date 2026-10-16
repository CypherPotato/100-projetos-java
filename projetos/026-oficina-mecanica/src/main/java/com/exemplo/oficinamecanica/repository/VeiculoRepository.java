package com.exemplo.oficinamecanica.repository;

import com.exemplo.oficinamecanica.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}

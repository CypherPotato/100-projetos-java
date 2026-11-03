package com.exemplo.empresamudancas.repository;

import com.exemplo.empresamudancas.model.Caminhao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaminhaoRepository extends JpaRepository<Caminhao, Long> {
}

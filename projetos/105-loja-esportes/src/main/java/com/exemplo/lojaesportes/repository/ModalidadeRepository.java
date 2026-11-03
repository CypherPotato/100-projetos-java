package com.exemplo.lojaesportes.repository;

import com.exemplo.lojaesportes.model.Modalidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModalidadeRepository extends JpaRepository<Modalidade, Long> {
}

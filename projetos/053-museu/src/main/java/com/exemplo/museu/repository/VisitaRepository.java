package com.exemplo.museu.repository;

import com.exemplo.museu.model.Visita;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitaRepository extends JpaRepository<Visita, Long> {

    List<Visita> findByExposicaoId(Long exposicaoId);
}

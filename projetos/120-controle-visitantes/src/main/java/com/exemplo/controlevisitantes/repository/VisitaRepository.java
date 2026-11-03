package com.exemplo.controlevisitantes.repository;

import com.exemplo.controlevisitantes.model.Visita;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitaRepository extends JpaRepository<Visita, Long> {

    List<Visita> findByAnfitriaoId(Long anfitriaoId);
}

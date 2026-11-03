package com.exemplo.escritorioadvocacia.repository;

import com.exemplo.escritorioadvocacia.model.Audiencia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudienciaRepository extends JpaRepository<Audiencia, Long> {

    List<Audiencia> findByProcessoId(Long processoId);
}

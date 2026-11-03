package com.exemplo.simuladosconcursos.repository;

import com.exemplo.simuladosconcursos.model.Simulado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimuladoRepository extends JpaRepository<Simulado, Long> {

    List<Simulado> findByConcursoId(Long concursoId);
}

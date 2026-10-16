package com.exemplo.cursosonline.repository;

import com.exemplo.cursosonline.model.Modulo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuloRepository extends JpaRepository<Modulo, Long> {

    List<Modulo> findByCursoId(Long cursoId);
}

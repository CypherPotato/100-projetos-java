package com.exemplo.brechosolidario.repository;

import com.exemplo.brechosolidario.model.Peca;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PecaRepository extends JpaRepository<Peca, Long> {

    List<Peca> findByCategoriaId(Long categoriaId);
}

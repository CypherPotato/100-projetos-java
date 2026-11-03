package com.exemplo.laticinio.repository;

import com.exemplo.laticinio.model.Lote;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoteRepository extends JpaRepository<Lote, Long> {

    List<Lote> findByColetaId(Long coletaId);
}

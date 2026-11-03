package com.exemplo.granjaavicola.repository;

import com.exemplo.granjaavicola.model.Lote;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoteRepository extends JpaRepository<Lote, Long> {

    List<Lote> findByGalpaoId(Long galpaoId);
}

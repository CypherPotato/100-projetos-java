package com.exemplo.granjaavicola.repository;

import com.exemplo.granjaavicola.model.Coleta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColetaRepository extends JpaRepository<Coleta, Long> {

    List<Coleta> findByLoteId(Long loteId);
}

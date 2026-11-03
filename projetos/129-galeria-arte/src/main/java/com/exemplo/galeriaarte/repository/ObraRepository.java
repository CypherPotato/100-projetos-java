package com.exemplo.galeriaarte.repository;

import com.exemplo.galeriaarte.model.Obra;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObraRepository extends JpaRepository<Obra, Long> {

    List<Obra> findByArtistaId(Long artistaId);
}

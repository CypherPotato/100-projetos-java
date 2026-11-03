package com.exemplo.gestaoobras.repository;

import com.exemplo.gestaoobras.model.Etapa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtapaRepository extends JpaRepository<Etapa, Long> {

    List<Etapa> findByObraId(Long obraId);
}

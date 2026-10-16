package com.exemplo.fazenda.repository;

import com.exemplo.fazenda.model.Plantio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantioRepository extends JpaRepository<Plantio, Long> {

    List<Plantio> findByTalhaoId(Long talhaoId);
}

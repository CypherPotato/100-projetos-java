package com.exemplo.hortacomunitaria.repository;

import com.exemplo.hortacomunitaria.model.Plantio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantioRepository extends JpaRepository<Plantio, Long> {

    List<Plantio> findByCanteiroId(Long canteiroId);
}

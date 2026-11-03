package com.exemplo.lojamoveis.repository;

import com.exemplo.lojamoveis.model.Movel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovelRepository extends JpaRepository<Movel, Long> {

    List<Movel> findByAmbienteId(Long ambienteId);
}

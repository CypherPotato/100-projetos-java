package com.exemplo.perfumaria.repository;

import com.exemplo.perfumaria.model.Perfume;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfumeRepository extends JpaRepository<Perfume, Long> {

    List<Perfume> findByFabricanteId(Long fabricanteId);
}

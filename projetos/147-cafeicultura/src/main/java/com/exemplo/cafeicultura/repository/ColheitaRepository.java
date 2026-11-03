package com.exemplo.cafeicultura.repository;

import com.exemplo.cafeicultura.model.Colheita;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColheitaRepository extends JpaRepository<Colheita, Long> {

    List<Colheita> findByTalhaoId(Long talhaoId);
}

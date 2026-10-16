package com.exemplo.apicultura.repository;

import com.exemplo.apicultura.model.Colheita;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColheitaRepository extends JpaRepository<Colheita, Long> {

    List<Colheita> findByColmeiaId(Long colmeiaId);
}

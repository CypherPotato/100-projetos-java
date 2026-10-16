package com.exemplo.apicultura.repository;

import com.exemplo.apicultura.model.Colmeia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColmeiaRepository extends JpaRepository<Colmeia, Long> {

    List<Colmeia> findByApiarioId(Long apiarioId);
}

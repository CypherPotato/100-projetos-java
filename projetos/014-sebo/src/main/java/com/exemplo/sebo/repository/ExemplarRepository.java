package com.exemplo.sebo.repository;

import com.exemplo.sebo.model.Exemplar;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExemplarRepository extends JpaRepository<Exemplar, Long> {

    List<Exemplar> findByGeneroId(Long generoId);
}

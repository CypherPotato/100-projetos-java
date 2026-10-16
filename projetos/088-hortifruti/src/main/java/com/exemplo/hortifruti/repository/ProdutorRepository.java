package com.exemplo.hortifruti.repository;

import com.exemplo.hortifruti.model.Produtor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutorRepository extends JpaRepository<Produtor, Long> {
}

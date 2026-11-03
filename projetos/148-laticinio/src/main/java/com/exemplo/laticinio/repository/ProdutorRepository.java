package com.exemplo.laticinio.repository;

import com.exemplo.laticinio.model.Produtor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutorRepository extends JpaRepository<Produtor, Long> {
}

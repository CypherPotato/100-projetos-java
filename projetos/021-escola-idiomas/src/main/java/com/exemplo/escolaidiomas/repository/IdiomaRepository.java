package com.exemplo.escolaidiomas.repository;

import com.exemplo.escolaidiomas.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdiomaRepository extends JpaRepository<Idioma, Long> {
}

package com.exemplo.escolamusica.repository;

import com.exemplo.escolamusica.model.Instrumento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentoRepository extends JpaRepository<Instrumento, Long> {
}

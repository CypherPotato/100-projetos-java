package com.exemplo.corretoraseguros.repository;

import com.exemplo.corretoraseguros.model.Segurado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeguradoRepository extends JpaRepository<Segurado, Long> {
}

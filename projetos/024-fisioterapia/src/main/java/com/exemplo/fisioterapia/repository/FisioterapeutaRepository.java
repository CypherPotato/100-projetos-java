package com.exemplo.fisioterapia.repository;

import com.exemplo.fisioterapia.model.Fisioterapeuta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FisioterapeutaRepository extends JpaRepository<Fisioterapeuta, Long> {
}

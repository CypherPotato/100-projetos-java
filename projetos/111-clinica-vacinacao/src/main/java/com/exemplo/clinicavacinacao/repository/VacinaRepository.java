package com.exemplo.clinicavacinacao.repository;

import com.exemplo.clinicavacinacao.model.Vacina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacinaRepository extends JpaRepository<Vacina, Long> {
}

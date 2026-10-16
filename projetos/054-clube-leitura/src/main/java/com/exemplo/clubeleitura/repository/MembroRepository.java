package com.exemplo.clubeleitura.repository;

import com.exemplo.clubeleitura.model.Membro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembroRepository extends JpaRepository<Membro, Long> {
}

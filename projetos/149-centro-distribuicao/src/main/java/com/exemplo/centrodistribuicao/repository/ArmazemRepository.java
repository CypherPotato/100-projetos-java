package com.exemplo.centrodistribuicao.repository;

import com.exemplo.centrodistribuicao.model.Armazem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArmazemRepository extends JpaRepository<Armazem, Long> {
}

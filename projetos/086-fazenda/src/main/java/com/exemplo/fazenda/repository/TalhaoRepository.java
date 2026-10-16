package com.exemplo.fazenda.repository;

import com.exemplo.fazenda.model.Talhao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TalhaoRepository extends JpaRepository<Talhao, Long> {
}

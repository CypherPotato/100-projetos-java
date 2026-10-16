package com.exemplo.investimentos.repository;

import com.exemplo.investimentos.model.Ativo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtivoRepository extends JpaRepository<Ativo, Long> {

    List<Ativo> findByCarteiraId(Long carteiraId);
}

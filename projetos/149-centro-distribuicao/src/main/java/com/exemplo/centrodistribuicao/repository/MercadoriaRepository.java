package com.exemplo.centrodistribuicao.repository;

import com.exemplo.centrodistribuicao.model.Mercadoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MercadoriaRepository extends JpaRepository<Mercadoria, Long> {

    List<Mercadoria> findByArmazemId(Long armazemId);
}

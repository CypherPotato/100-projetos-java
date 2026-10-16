package com.exemplo.fisioterapia.repository;

import com.exemplo.fisioterapia.model.Sessao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    List<Sessao> findByFisioterapeutaId(Long fisioterapeutaId);
}

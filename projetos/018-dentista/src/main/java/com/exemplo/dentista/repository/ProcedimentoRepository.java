package com.exemplo.dentista.repository;

import com.exemplo.dentista.model.Procedimento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {

    List<Procedimento> findByDentistaId(Long dentistaId);
}

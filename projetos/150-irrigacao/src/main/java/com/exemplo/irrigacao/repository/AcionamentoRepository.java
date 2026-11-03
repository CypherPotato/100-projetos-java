package com.exemplo.irrigacao.repository;

import com.exemplo.irrigacao.model.Acionamento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcionamentoRepository extends JpaRepository<Acionamento, Long> {

    List<Acionamento> findByAreaId(Long areaId);
}

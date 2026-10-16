package com.exemplo.frotaagricola.repository;

import com.exemplo.frotaagricola.model.Manutencao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {

    List<Manutencao> findByMaquinaId(Long maquinaId);
}

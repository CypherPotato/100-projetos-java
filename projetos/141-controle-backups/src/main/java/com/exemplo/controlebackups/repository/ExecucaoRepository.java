package com.exemplo.controlebackups.repository;

import com.exemplo.controlebackups.model.Execucao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecucaoRepository extends JpaRepository<Execucao, Long> {

    List<Execucao> findByRotinaId(Long rotinaId);
}

package com.exemplo.nutricionista.repository;

import com.exemplo.nutricionista.model.Avaliacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    List<Avaliacao> findByPacienteId(Long pacienteId);
}

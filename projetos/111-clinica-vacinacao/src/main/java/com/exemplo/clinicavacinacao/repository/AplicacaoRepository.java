package com.exemplo.clinicavacinacao.repository;

import com.exemplo.clinicavacinacao.model.Aplicacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AplicacaoRepository extends JpaRepository<Aplicacao, Long> {

    List<Aplicacao> findByVacinaId(Long vacinaId);
}

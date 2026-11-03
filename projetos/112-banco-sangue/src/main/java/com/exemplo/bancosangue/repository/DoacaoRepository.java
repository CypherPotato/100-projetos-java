package com.exemplo.bancosangue.repository;

import com.exemplo.bancosangue.model.Doacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoacaoRepository extends JpaRepository<Doacao, Long> {

    List<Doacao> findByDoadorId(Long doadorId);
}

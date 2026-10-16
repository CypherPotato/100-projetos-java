package com.exemplo.campanhadoacoes.repository;

import com.exemplo.campanhadoacoes.model.Doacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoacaoRepository extends JpaRepository<Doacao, Long> {

    List<Doacao> findByCampanhaId(Long campanhaId);

    List<Doacao> findByDoadorId(Long doadorId);
}

package com.exemplo.teatro.repository;

import com.exemplo.teatro.model.Apresentacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApresentacaoRepository extends JpaRepository<Apresentacao, Long> {

    List<Apresentacao> findByPecaId(Long pecaId);
}

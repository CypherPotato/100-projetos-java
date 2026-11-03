package com.exemplo.pesquisasatisfacao.repository;

import com.exemplo.pesquisasatisfacao.model.Resposta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {

    List<Resposta> findByPesquisaId(Long pesquisaId);
}

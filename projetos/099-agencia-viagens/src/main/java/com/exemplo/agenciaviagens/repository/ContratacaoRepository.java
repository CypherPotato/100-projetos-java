package com.exemplo.agenciaviagens.repository;

import com.exemplo.agenciaviagens.model.Contratacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratacaoRepository extends JpaRepository<Contratacao, Long> {

    List<Contratacao> findByPacoteId(Long pacoteId);

    List<Contratacao> findByClienteId(Long clienteId);
}

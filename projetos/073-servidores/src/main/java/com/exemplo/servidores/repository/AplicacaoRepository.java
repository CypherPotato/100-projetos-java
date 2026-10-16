package com.exemplo.servidores.repository;

import com.exemplo.servidores.model.Aplicacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AplicacaoRepository extends JpaRepository<Aplicacao, Long> {

    List<Aplicacao> findByServidorId(Long servidorId);
}

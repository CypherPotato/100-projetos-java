package com.exemplo.dedetizadora.repository;

import com.exemplo.dedetizadora.model.Aplicacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AplicacaoRepository extends JpaRepository<Aplicacao, Long> {

    List<Aplicacao> findByClienteId(Long clienteId);
}

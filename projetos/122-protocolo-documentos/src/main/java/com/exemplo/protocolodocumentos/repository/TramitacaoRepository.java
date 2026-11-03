package com.exemplo.protocolodocumentos.repository;

import com.exemplo.protocolodocumentos.model.Tramitacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TramitacaoRepository extends JpaRepository<Tramitacao, Long> {

    List<Tramitacao> findByDocumentoId(Long documentoId);
}

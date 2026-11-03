package com.exemplo.ouvidoria.repository;

import com.exemplo.ouvidoria.model.Manifestacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManifestacaoRepository extends JpaRepository<Manifestacao, Long> {

    List<Manifestacao> findByDepartamentoId(Long departamentoId);
}

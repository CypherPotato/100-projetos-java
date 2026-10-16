package com.exemplo.otica.repository;

import com.exemplo.otica.model.Armacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArmacaoRepository extends JpaRepository<Armacao, Long> {

    List<Armacao> findByMarcaId(Long marcaId);
}

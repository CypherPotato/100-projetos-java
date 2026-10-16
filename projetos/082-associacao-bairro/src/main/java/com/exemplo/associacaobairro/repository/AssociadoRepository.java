package com.exemplo.associacaobairro.repository;

import com.exemplo.associacaobairro.model.Associado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    List<Associado> findByRuaId(Long ruaId);
}

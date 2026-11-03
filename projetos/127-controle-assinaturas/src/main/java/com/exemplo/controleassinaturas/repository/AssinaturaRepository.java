package com.exemplo.controleassinaturas.repository;

import com.exemplo.controleassinaturas.model.Assinatura;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssinaturaRepository extends JpaRepository<Assinatura, Long> {

    List<Assinatura> findByCategoriaId(Long categoriaId);
}
